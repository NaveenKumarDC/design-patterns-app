package com.naveendc.payment.service;

/**
 * Created by Naveen Kumar D C on 07/03/25
 */
public interface PaymentStrategy {
  /**
   * Abstract method for pay
   *
   * @param amount
   */
  void pay(double amount);

}
