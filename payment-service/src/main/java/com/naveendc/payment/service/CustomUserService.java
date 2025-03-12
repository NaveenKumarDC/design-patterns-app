package com.naveendc.payment.service;

import com.naveendc.payment.entity.User;
import com.naveendc.payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Naveen Kumar D C on 11/03/25
 */
@Service
public class CustomUserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User dbUser = userRepository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
      return new org.springframework.security.core.userdetails.User(
              dbUser.getUsername(),
              dbUser.getPassword(), // Hashed password from DB
              getAuthorities(dbUser)
      );
    }

  private Collection<? extends GrantedAuthority> getAuthorities(User user) {
    return user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role))
            .collect(Collectors.toList());
  }
}
