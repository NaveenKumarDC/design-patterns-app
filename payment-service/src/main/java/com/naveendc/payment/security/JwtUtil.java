package com.naveendc.payment.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

/**
 * Created by Naveen Kumar D C on 07/03/25
 */
@Component
public class JwtUtil {
  private final String SECRET_KEY = "your-very-secure-and-long-secret-key-for-jwt-authentication-payment-service-test-auth-header-token";

  MacAlgorithm macAlgorithm = Jwts.SIG.HS256;

  // Generate JWT Token
  public String generateToken(String username) {
    // Generate a secure key (at least 32 bytes for HS256)
    // Create a JWT token (No need to specify the algorithm explicitly)
    String jwt = Jwts.builder()
            .subject(username)
            .issuedAt(new Date())  //
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiry
            .signWith(getSigningKey()) // The algorithm is inferred from the key
            .compact();

    System.out.println("Generated JWT: " + jwt);
    return jwt;
  }

  // Validate Token
  public boolean validateToken(String token, String username) {
    return extractUsername(token).equals(username) && !isTokenExpired(token);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private SecretKey getSigningKey() {
    String base64EncodedSecret = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

    //Use parserBuilder() with a key
    Claims claims = Jwts.parser() // If still not recognized, ensure dependencies are correct
            .verifyWith(getSigningKey()) // ✅ Replaces setSigningKey() in 0.12.6
            .build()
            .parseSignedClaims(token) // ✅ Replaces parseClaimsJws()
            .getPayload();
    return claimsResolver.apply(claims);
  }
}

