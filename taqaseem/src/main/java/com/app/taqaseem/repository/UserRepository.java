package com.app.taqaseem.repository;

import com.app.taqaseem.model.UserInfo;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {
    Optional<UserInfo> findById(String Id);

    Optional<UserInfo> findByPhoneNumber(String phoneNumber);

    Optional<UserInfo> findByUsername(String username);

    Optional<UserInfo> findByName(String name);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);
}
