package com.hymns.hymns.service;

import com.hymns.hymns.dto.PaymentDto;
import com.hymns.hymns.entity.Payment;

import java.util.List;

public interface PaymentService {
    String addPayment(PaymentDto paymentDto);

    List<PaymentDto> getAllPayments();

    PaymentDto getPayment(int id);

    List<PaymentDto> getUserPayments();

    void changeStatus(int id);

    String khaltiPayment(Payment payment) throws Exception;

    PaymentDto verifyPayment(String token, int id) throws Exception;
}
