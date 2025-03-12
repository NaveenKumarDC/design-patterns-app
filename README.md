
# Payment Processing System (Spring Boot)

## Overview
This project is an **Enterprise-Level Payment Processing System** built using **Spring Boot**, implementing the **Strategy Pattern** for handling multiple payment methods dynamically. The system is designed to be scalable, maintainable, and easily extensible for adding new payment methods.

## Spring Boot 3 - JWT Authentication

This project also demonstrates how to implement **JWT (JSON Web Token) authentication** in a **Spring Boot 3** application using `io.jsonwebtoken:jjwt 0.12+`.

## Tech Stack
- **Spring Boot 3**
- **Spring Dependency Injection (DI)**
- **Java 17**
- **Spring Data JPA** (PostgreSQL)
- **JWT Authentication**
- **Spring Security**
- **Lombok**
- **RabbitMQ / Kafka** (for asynchronous transaction processing)

---

## **Use Case: Payment Processing System**
### **Problem Statement**
A company needs a **flexible** payment processing system that supports multiple payment methods, such as **Credit Card, UPI, and PayPal**. The system should be able to **dynamically select the appropriate payment strategy** based on user input while maintaining loose coupling.

### **Solution: Strategy Pattern with Spring DI**
The **Strategy Pattern** is used to define multiple payment methods as independent components, injected dynamically at runtime using **Spring Dependency Injection (DI)**.

---

## **Architecture & Design**
### **Class Diagram Representation**
```
                    +--------------------+
                    |  PaymentService    |
                    |--------------------|
                    |  processPayment()  |
                    +---------+----------+
                              |
      -------------------------------------------------
      |                      |                        |
+----------------+   +----------------+   +----------------+
| CreditCardPayment |   | UPIPayment       |   | PayPalPayment   |
|----------------|   |----------------|   |----------------|
| pay()         |   | pay()          |   | pay()         |
+----------------+   +----------------+   +----------------+
```

---

## **Implementation Details**

### **1. Define Payment Strategy Interface**
```java
public interface PaymentStrategy {
    void pay(double amount);
}
```

### **2. Implement Different Payment Strategies**
#### **Credit Card Payment Implementation**
```java
@Component("creditCard")
public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}
```

#### **UPI Payment Implementation**
```java
@Component("upi")
public class UPIPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using UPI.");
    }
}
```

#### **PayPal Payment Implementation**
```java
@Component("paypal")
public class PayPalPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal.");
    }
}
```

### **3. Implement Payment Service with Dynamic Strategy Selection**
```java
@Service
public class PaymentService {
    private final Map<String, PaymentStrategy> paymentStrategies;

    @Autowired
    public PaymentService(Map<String, PaymentStrategy> strategies) {
        this.paymentStrategies = strategies;
    }

    public void processPayment(String method, double amount) {
        PaymentStrategy strategy = paymentStrategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid Payment Method: " + method);
        }
        strategy.pay(amount);
    }
}
```

### **4. Expose REST API for Payment Processing**
```java
@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> makePayment(@RequestParam String method, @RequestParam double amount) {
        paymentService.processPayment(method, amount);
        return ResponseEntity.ok("Payment of " + amount + " via " + method + " was successful.");
    }
}
```

---

## **Testing the API**
### **1. Make a Payment via Credit Card**
```http
POST /payments/pay?method=creditCard&amount=500
```
📌 **Response:**
```json
{"message": "Payment of 500.0 via creditCard was successful."}
```

### **2. Make a Payment via UPI**
```http
POST /payments/pay?method=upi&amount=200
```
📌 **Response:**
```json
{"message": "Payment of 200.0 via upi was successful."}
```

### **3. Make a Payment via PayPal**
```http
POST /payments/pay?method=paypal&amount=1000
```
📌 **Response:**
```json
{"message": "Payment of 1000.0 via paypal was successful."}
```

---

## **Benefits of Using Strategy Pattern**
✅ **Extensibility:** Easily add new payment methods without modifying existing logic.  
✅ **Loose Coupling:** Each payment method is independent and injected at runtime.  
✅ **Single Responsibility:** Each strategy handles only one type of payment.  
✅ **Spring DI Advantage:** Spring automatically injects the correct implementation based on user selection.

---

## **Future Enhancements**
- ✅ **Integrate Payment Gateway APIs (Stripe, Razorpay, PayPal SDK)**
- ✅ **Implement Async Processing with RabbitMQ/Kafka**
- ✅ **Add Security with JWT and Role-Based Access Control (RBAC)**
- ✅ **Introduce Database Transactions for Payment Logs**

---

## **Conclusion**
This project successfully demonstrates an **Enterprise-Level Payment Processing System** using **Spring Boot** and **Strategy Pattern with Dependency Injection**. The approach makes it **modular, maintainable, and scalable** for future expansion. 🚀

---

💡 **Need Help?** Open an issue or reach out! Happy coding! 🎯

# Spring Boot 3 - JWT Authentication

This project also demonstrates how to implement **JWT (JSON Web Token) authentication** in a **Spring Boot 3** application using `io.jsonwebtoken:jjwt 0.12+`.

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


