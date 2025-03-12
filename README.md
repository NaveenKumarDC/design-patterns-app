# Spring Boot 3 - JWT Authentication

This project demonstrates how to implement **JWT (JSON Web Token) authentication** in a **Spring Boot 3** application using `io.jsonwebtoken:jjwt 0.12+`.

## 🚀 Features
- Secure **JWT-based authentication**
- Uses **jjwt 0.12+** with updated methods
- Implements **token validation and expiration handling**
- Supports **Spring Boot 3**

---

## 🛠️ Tech Stack
- **Java 17+**
- **Spring Boot 3**
- **Spring Security**
- **jjwt 0.12.3** (JSON Web Token Library)

---

## 📦 Dependencies
Add the following dependencies to `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- JJWT (JSON Web Token) -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.3</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.3</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.3</version>
    </dependency>
</dependencies>
```

---

## 🔑 JWT Utility Class
Create `JwtUtil.java` to **generate and validate JWT tokens**.

```java
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final Key secretKey = Keys.secretKeyFor(Jwts.SIG.HS256);

    // Generate JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(Instant.now())
                .expiration(Instant.now().plusSeconds(3600)) // 1 hour expiry
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    // Extract Username from Token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract Expiration Date
    public Date extractExpiration(String token) {
        return Date.from(extractClaims(token).getExpiration());
    }

    // Validate Token
    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // Check if Token is Expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract All Claims
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
```

---

## 🔒 Secure REST Controller
Create `AuthController.java` to generate a token and test authentication.

```java
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
```

---

## 🔧 Configuring Spring Security (Optional)
If you want to secure all endpoints, configure Spring Security:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
```

---

## 📋 API Endpoints

### 1️⃣ **Login and Generate JWT**
**Request:**
```http
POST /api/auth/login
Content-Type: application/json
{
  "username": "naveen"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 2️⃣ **Access a Protected Endpoint (After Login)**
Use the token in the `Authorization` header:
```http
GET /api/protected-endpoint
Authorization: Bearer <your-jwt-token>
```

---

## 🚀 Running the Application
1. **Clone the Repository:**
   ```sh
   git clone https://github.com/NaveenKumarDC/design-patterns-app
   cd design-patterns-app
   ```

2. **Build and Run:**
   ```sh
   mvn spring-boot:run
   ```

3. **Test with Postman or Curl:**
   ```sh
   curl -X POST http://localhost:8080/api/auth/login \
        -H "Content-Type: application/json" \
        -d '{"username":"naveen"}'
   ```

---

## 🔥 Conclusion
This guide provides a **JWT authentication system** using **Spring Boot 3 and jjwt 0.12+**. You can now secure your APIs and authenticate users effectively.

💡 **Next Steps:**
- Integrate with **Spring Security AuthenticationManager** for full user authentication.
- Store **tokens in a database** for session tracking.
- Add **refresh tokens** for long-lived authentication.

---

### 🎯 Need Help?
If you have questions, feel free to ask! 🚀


