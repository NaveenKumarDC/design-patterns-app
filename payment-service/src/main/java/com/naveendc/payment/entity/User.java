package com.naveendc.payment.entity;


import jakarta.persistence.*;

import java.util.Set;

/**
 * Created by Naveen Kumar D C on 11/03/25
 */
@Entity
@Table(name = "users") // Table name in DB
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password; // Store encoded password (BCrypt)

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
  @Column(name = "role")
  private Set<String> roles; // e.g., ["ROLE_USER", "ROLE_ADMIN"]

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }
}

