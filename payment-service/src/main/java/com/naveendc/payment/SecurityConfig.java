package com.naveendc.payment;

import com.naveendc.payment.security.JwtFilter;
import com.naveendc.payment.security.JwtUtil;
import com.naveendc.payment.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Naveen Kumar D C on 07/03/25
 */
@Configuration
@ComponentScan(basePackages = "com.naveendc.payment.service")
public class SecurityConfig {


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
//                    .anyRequest().permitAll() // Allow all requests
                            .requestMatchers("/auth/login").permitAll()
                            .requestMatchers("/v1/payment/**").authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter(userDetailsService(), jwtUtil()), UsernamePasswordAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF if using POST/PUT requests
            .formLogin(AbstractHttpConfigurer::disable) // Disable login form
            .httpBasic(AbstractHttpConfigurer::disable); // Disable basic auth

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserService(); // ✅ Manually create the bean
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtFilter jwtFilter(@Lazy UserDetailsService userDetailsService, JwtUtil jwtUtil) {
    return new JwtFilter(jwtUtil, userDetailsService);
  }

  @Bean
  public JwtUtil jwtUtil() {
    return new JwtUtil();
  }
}
