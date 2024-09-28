package com.hymns.hymns.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hymns.hymns.dto.PaymentDto;
import com.hymns.hymns.entity.Payment;
import com.hymns.hymns.repository.PaymentRepo;
import com.hymns.hymns.repository.RentalRepo;
import com.hymns.hymns.repository.UserRepo;
import com.hymns.hymns.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;
    private final RentalRepo rentalRepo;

    @Override
    public String addPayment(PaymentDto paymentDto) {
        try {
            Payment payment = new Payment();
            payment.setPaymentAmount(paymentDto.getPaymentAmount());
            Date date = new Date();
            payment.setPaymentDate(date);
            payment.setPaymentStatus(paymentDto.getPaymentStatus());


            payment.setUser(
                    userRepo.findById(paymentDto.getUser().getId())
                            .orElseThrow(() -> new RuntimeException("User not found"))
            );
            payment.setRental(
                    rentalRepo.findById(Math.toIntExact(paymentDto.getRental().getId()))
                            .orElseThrow(() -> new RuntimeException("Rental not found"))
            );
            paymentRepo.save(payment);
            if (payment.getPaymentType().equals("online")) {
                return khaltiPayment(payment);
            }
            return "Payment added successfully";

        } catch (Exception e) {
            throw new RuntimeException("Error while adding payment: " + e.getMessage());
        }

    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return List.of();
    }

    @Override
    public PaymentDto getPayment(int id) {
        return null;
    }

    @Override
    public List<PaymentDto> getUserPayments() {
        return List.of();
    }

    @Override
    public void changeStatus(int id) {

    }

    @Override
    public String khaltiPayment(Payment payment) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Prepare the payment data
            Map<String, Object> data = Map.of(
                    "return_url", "http://localhost:3000",
                    "website_url", "http://localhost:3000",
                    "amount", payment.getPaymentAmount() * 100,
                    "purchase_order_name", "Rental Payment",
                    "purchase_order_id", payment.getPaymentId(),
                    "product_name", payment.getRental().getInstrument().getInstrumentName(),
                    "product_url", "http://localhost:3000",
                    "customer_info", Map.of(
                            "name", payment.getUser().getFirstName() + " " + payment.getUser().getLastName(),
                            "email", payment.getUser().getEmail()
                    )
            );

            // Convert data to JSON
            String requestBody = mapper.writeValueAsString(data);

            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://a.khalti.com/api/v2/epayment/initiate/"))  // Replace with the correct Khalti API endpoint
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Key 66aa33728e7d4eeab705f9b8922defc6")  // Replace with the actual authorization key
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send HTTP request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Return pidx

            return response.body();
        } catch (Exception e) {
            throw new Exception("Error while making payment: " + e.getMessage());
        }
    }
}
