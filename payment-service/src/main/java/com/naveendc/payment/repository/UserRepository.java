package com.naveendc.payment.repository;

import com.naveendc.payment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Created by Naveen Kumar D C on 11/03/25
 */
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.username = :username")
  Optional<User> findByUsername(String username); // ✅ Find user by username
}

