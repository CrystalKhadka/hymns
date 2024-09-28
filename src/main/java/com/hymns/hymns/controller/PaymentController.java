package com.hymns.hymns.controller;


import com.hymns.hymns.dto.PaymentDto;
import com.hymns.hymns.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/verify/{id}")
    public ResponseEntity<Map<String, Object>> verifyPayment(@PathVariable int id, @RequestBody Object request) {
        try {
//           Get data from map
            String token = (String) ((Map) request).get("token");
            System.out.println("request = " + token);
            PaymentDto paymentDto = paymentService.verifyPayment(token, id);
            return ResponseEntity.ok(Map.of("message", "Payment verified successfully",
                    "payment", paymentDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
