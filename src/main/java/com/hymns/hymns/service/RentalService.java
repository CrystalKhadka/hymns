package com.hymns.hymns.service;

import com.hymns.hymns.dto.RentalDto;
import com.hymns.hymns.entity.Rental;

import java.util.List;

public interface RentalService {

    RentalDto rentInstrument(RentalDto rentalDto);

    List<RentalDto> getAllRentals();

    List<RentalDto> getAllRentalsByUser(int id);

    void returnInstrument(int id);

    List<Rental> getActiveRentals();
}
