package com.hymns.hymns.controller;


import com.hymns.hymns.dto.PaymentDto;
import com.hymns.hymns.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addPayment(@RequestBody PaymentDto paymentDto) {
        String response = paymentService.addPayment(paymentDto);
        System.out.println("response = " + response);
        if (response == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error while adding payment"));
        }

        return ResponseEntity.ok(Map.of("message", "Payment added successfully",
                "paymentId", response))
                ;
    }
}
