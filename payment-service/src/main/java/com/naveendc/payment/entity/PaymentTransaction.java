package com.naveendc.payment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by Naveen Kumar D C on 07/03/25
 */
@Data
@Entity
@Table(name = "payment_transactions")
public class PaymentTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String method;
  private double amount;
  private LocalDateTime timestamp;

  // Constructors
  public PaymentTransaction() {
  }

  public PaymentTransaction(String method, double amount) {
    this.method = method;
    this.amount = amount;
    this.timestamp = LocalDateTime.now();
  }
}