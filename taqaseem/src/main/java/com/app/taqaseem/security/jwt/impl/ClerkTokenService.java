package com.app.taqaseem.security.jwt.impl;

import com.app.taqaseem.exception.UserAuthenticationException;
import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.repository.UserRepository;
import com.app.taqaseem.security.TaqaseemUserDetails;
import com.app.taqaseem.security.jwt.TokenService;
import com.clerk.backend_api.Clerk;
import com.clerk.backend_api.models.components.*;
import com.clerk.backend_api.models.operations.GetUserResponse;
import com.clerk.backend_api.models.operations.VerifyClientRequestBody;
import com.clerk.backend_api.models.operations.VerifyClientResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Profile({"local-clerk"})
@RequiredArgsConstructor
@Log4j2
public class ClerkTokenService implements TokenService {
  private final Clerk clerkClient;
  private final UserRepository userRepository;

  @Override
  public String extractUserIdentifier(String token) {
    return getActiveClerkUserId(token);
  }

  @Override
  public boolean isTokenInvalid(String token, TaqaseemUserDetails userDetails) {
    String userId = getActiveClerkUserId(token);
    return userId == null;
  }

  @Override
  public boolean isTokenInactive(String token, String userId) {
    return false;
  }

  @Override
  public TaqaseemUserDetails getUserDetailsFromToken(String token) {
    String userId = this.getActiveClerkUserId(token);
    if (userId == null) {
      throw new UserAuthenticationException("No active clerk user found");
    }
    try {
      GetUserResponse userRes = clerkClient.users().get().userId(userId).call();
      if (userRes.user().isPresent()) {
        User clerkUser = userRes.user().get();
        return userRepository.findById(userId).orElseGet(() -> createUserFromClerkToken(clerkUser));
      }
      return null;
    } catch (Exception e) {
      throw new UserAuthenticationException("Fetching user failed", e);
    }
  }

  @Override
  public String generateAccessToken(TaqaseemUserDetails userDetails) {
    throw new UnsupportedOperationException("Token generation is handled by Clerk");
  }

  @Override
  public String generateRefreshToken(TaqaseemUserDetails userDetails) {
    throw new UnsupportedOperationException("Token generation is handled by Clerk");
  }

  private String getActiveClerkUserId(String token) {
    try {
      VerifyClientRequestBody req = VerifyClientRequestBody.builder().token(token).build();
      VerifyClientResponse res = clerkClient.clients().verify().request(req).call();
      if (res.client().isPresent()) {
        Client client = res.client().get();
        if (CollectionUtils.isEmpty(client.sessions())) {
          return null;
        }
        List<Session> sessions =
            client.sessions().stream()
                .filter(session -> Objects.equals(session.status(), Status.ACTIVE))
                .toList();
        if (CollectionUtils.isEmpty(sessions)) {
          return null;
        }
        return sessions.stream().findFirst().map(Session::userId).orElse(null);
      }
      return null;
    } catch (Exception e) {
      log.error("Error extracting user identifier from Clerk token", e);
      throw new UserAuthenticationException("Invalid Clerk token: " + e.getMessage());
    }
  }

  private UserInfo createUserFromClerkToken(User user) {

    UserInfo newUser =
        UserInfo.builder()
            .id(user.id())
            .email(
                user.emailAddresses().stream()
                    .findFirst()
                    .map(EmailAddress::emailAddress)
                    .orElse(null))
            .phoneNumber(
                user.phoneNumbers().stream().findFirst().map(PhoneNumber::phoneNumber).orElse(null))
            .name(user.firstName().orElse(null))
            .username(user.username().orElse(null))
            .registered(true)
            .build();

    return userRepository.save(newUser);
  }
}
