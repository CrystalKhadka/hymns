package com.hymns.hymns.controller;


import com.hymns.hymns.dto.RentalDto;
import com.hymns.hymns.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/rent")
    public ResponseEntity<Map<String, String>> rentInstrument(@RequestBody RentalDto rentalDto, @RequestHeader("Authorization") String token) {
        try {
            rentalService.rentInstrument(rentalDto);
            return ResponseEntity.ok(Map.of("message", "Instrument rented successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllRentals() {
        try {
            List<RentalDto> rentals = rentalService.getAllRentals();
            return ResponseEntity.ok(Map.of("rentals", rentals));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> getAllRentalsByUser(@PathVariable int id) {
        try {
            List<RentalDto> rentals = rentalService.getAllRentalsByUser(id);
            return ResponseEntity.ok(Map.of("rentals", rentals));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
