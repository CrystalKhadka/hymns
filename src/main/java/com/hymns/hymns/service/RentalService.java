package com.hymns.hymns.service;

import com.hymns.hymns.dto.RentalDto;

import java.util.List;

public interface RentalService {

    void rentInstrument(RentalDto rentalDto);

    List<RentalDto> getAllRentals();

    List<RentalDto> getAllRentalsByUser(int id);
}
