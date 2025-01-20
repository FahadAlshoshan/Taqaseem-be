package com.app.taqaseem.repository;

import com.app.taqaseem.model.UserInfo;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
  Optional<UserInfo> findByPhoneNumber(String phoneNumber);
}
