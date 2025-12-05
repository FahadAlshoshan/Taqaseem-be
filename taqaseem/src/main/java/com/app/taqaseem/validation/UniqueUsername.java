package com.app.taqaseem.validation;


import com.app.taqaseem.repository.UserRepository;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.*;

import static com.app.taqaseem.constant.Messages.USERNAME_TAKEN_MESSAGE;

@Documented
@Constraint(validatedBy = UniqueUsername.UserNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default USERNAME_TAKEN_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @RequiredArgsConstructor
    class UserNameValidator implements ConstraintValidator<UniqueUsername, String> {
        private final UserRepository userRepository;

        @Override
        public boolean isValid(String username, ConstraintValidatorContext context) {
            if (username == null || username.isBlank()) {
                return true;
            }
            return !userRepository.existsByUsername(username);
        }
    }
}
