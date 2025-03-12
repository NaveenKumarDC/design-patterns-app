package com.naveendc.payment.service;

import org.springframework.stereotype.Service;

/**
 * Created by Naveen Kumar D C on 07/03/25
 * The @Service("beanName") annotation registers each class as a Spring bean with a specific name.
 */
@Service("creditCard")
public class CreditCardPaymentService implements PaymentStrategy {

  @Override
  public void pay(double amount) {
    System.out.println("Processing ₹" + amount + " via CreditCard.");
  }
}
