package com.naveendc.payment.controller;

import com.naveendc.payment.entity.PaymentTransaction;
import com.naveendc.payment.repository.PaymentTransactionRepository;
import com.naveendc.payment.service.PaymentContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Naveen Kumar D C on 07/03/25
 */
@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

  private final PaymentContext paymentContext;
  private final PaymentTransactionRepository transactionRepository;


  public PaymentController(PaymentContext paymentContext, PaymentTransactionRepository transactionRepository) {
    this.paymentContext = paymentContext;
    this.transactionRepository = transactionRepository;
  }

  @PostMapping("/pay")
  public String processPayment(@RequestParam String method, @RequestParam double amount) {
    paymentContext.executePayment(method, amount);
    return "Payment of ₹" + amount + " using " + method + " is being processed.";
  }

  // Retrieve all transactions
  @GetMapping("/transactions")
  public ResponseEntity<List<PaymentTransaction>> getAllTransactions() {
    List<PaymentTransaction> paymentTransactions = transactionRepository.findAll();
    return ResponseEntity.ok(paymentTransactions);
  }
}
