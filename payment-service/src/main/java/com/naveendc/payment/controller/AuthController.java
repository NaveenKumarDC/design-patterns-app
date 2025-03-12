package com.naveendc.payment.controller;

import com.naveendc.payment.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * Created by Naveen Kumar D C on 07/03/25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtUtil jwtUtil;

  public AuthController(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public Map<String, String> login (@RequestParam String userName) {
    String token = jwtUtil.generateToken(userName);
    return Map.of("token", token);
  }

}
