package com.app.taqaseem.repository;

import com.app.taqaseem.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByPhoneNumber(String phoneNumber);

  Optional<User> findByName(String name);

  @Query("SELECT u FROM User u WHERE u.name LIKE %:searchTerm%")
  List<User> findByNameContaining(@Param("searchTerm") String searchTerm);


}
