package com.naveendc.payment.service;

import com.naveendc.payment.entity.PaymentTransaction;
import com.naveendc.payment.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Naveen Kumar D C on 07/03/25
 * Spring injects all beans of type PaymentStrategy into a Map<String, PaymentStrategy> where the key is the bean name.
 */
@Service
public class PaymentContext {
  private final Map<String, PaymentStrategy> paymentStrategies;
  private final PaymentTransactionRepository transactionRepository;

  // Spring injects all PaymentStrategy beans into this map
  public PaymentContext(Map<String, PaymentStrategy> paymentStrategies, PaymentTransactionRepository transactionRepository) {
    this.paymentStrategies = paymentStrategies;
    this.transactionRepository = transactionRepository;
  }

  // Execute payment dynamically based on method selection
  public void executePayment(String method, double amount) {
    PaymentStrategy strategy = paymentStrategies.get(method);
    if (strategy == null) {
      System.out.println("Invalid payment method: " + method);
    } else {
      strategy.pay(amount);
      // Save transaction to DB
      PaymentTransaction transaction = new PaymentTransaction(method, amount);
      transactionRepository.save(transaction);
      System.out.println("Transaction saved successfully.");
    }
  }
}
