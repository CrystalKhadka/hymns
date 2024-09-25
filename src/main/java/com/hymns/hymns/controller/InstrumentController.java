package com.hymns.hymns.controller;

import com.hymns.hymns.dto.InstrumentDto;
import com.hymns.hymns.service.InstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/instrument")
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService instrumentService;

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Map<String, String>> add(@RequestBody InstrumentDto instrumentDto) {
        try {
            if (instrumentDto == null) {
                return new ResponseEntity<>(Map.of("message", "Please provide user details", "success", "false"), HttpStatus.BAD_REQUEST);
            }
            if (instrumentDto.getInstrumentName() == null || instrumentDto.getInstrumentType() == null ||
                    instrumentDto.getInstrumentRentalPrice() == 0 || instrumentDto.getInstrumentCondition() == null) {
                return new ResponseEntity<>(Map.of("message", "Please provide all required instrument details", "success", "false"), HttpStatus.BAD_REQUEST);
            }

            System.out.println(instrumentDto.getInstrumentName());
            // Call login service and get token
            instrumentService.addInstrument(instrumentDto);

            // Return success response with token
            return new ResponseEntity<>(Map.of(
                    "message", "Instrument added successfully",
                    "success", "true"
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    //    save image
    @PostMapping(value = "/save", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> save(@RequestPart(required = false) MultipartFile file) {
        try {
            if (file == null) {
                return new ResponseEntity<>(Map.of("message", "Please provide user details", "success", "false"), HttpStatus.BAD_REQUEST);
            }
            if (file.isEmpty()) {
                return new ResponseEntity<>(Map.of("message", "Please provide all required instrument details", "success", "false"), HttpStatus.BAD_REQUEST);
            }

            System.out.println(file.getOriginalFilename());
            // Call login service and get token
            String image = instrumentService.saveImage(file);

            // Return success response with token
            return new ResponseEntity<>(Map.of(
                    "message", "Image saved successfully",
                    "success", "true",
                    "image_name", image
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAll() {
        try {
            // Call get service and get token
            return new ResponseEntity<>(Map.of(
                    "message", "All instruments fetched successfully",
                    "success", "true",
                    "instruments", instrumentService.getAllInstruments()
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable int id) {
        try {
            // Call get service and get token
            return new ResponseEntity<>(Map.of(
                    "message", "Instrument fetched successfully",
                    "success", "true",
                    "instrument", instrumentService.getInstrument(id)
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


}
