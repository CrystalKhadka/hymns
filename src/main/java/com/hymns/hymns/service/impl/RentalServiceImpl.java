package com.hymns.hymns.service.impl;

import com.hymns.hymns.dto.InstrumentDto;
import com.hymns.hymns.dto.RentalDto;
import com.hymns.hymns.dto.UserDto;
import com.hymns.hymns.entity.Instrument;
import com.hymns.hymns.entity.Rental;
import com.hymns.hymns.entity.User;
import com.hymns.hymns.repository.InstrumentRepo;
import com.hymns.hymns.repository.RentalRepo;
import com.hymns.hymns.repository.UserRepo;
import com.hymns.hymns.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepo rentalRepo;
    private final JWTService jwtService;
    private final UserRepo userRepo;
    private final InstrumentRepo instrumentRepo;

    @Override
    public void rentInstrument(RentalDto rentalDto) {
        try {
            Rental rental = new Rental();


            User user = userRepo.findById(rentalDto.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User with email " + rentalDto.getUser() + " not found"));

            rental.setUser(user);
            Instrument instrument = instrumentRepo.findById(rentalDto.getInstrument().getInstrumentId())
                    .orElseThrow(() -> new RuntimeException("Instrument not found"));

            rental.setRentalDate(rentalDto.getRentalDate());
            rental.setInstrument(instrument);
            rental.setReturnDate(rentalDto.getReturnDate());
            rental.setStatus("Pending");

            instrument.setInstrumentRentalStatus("Rented");
            instrumentRepo.save(instrument);
            rentalRepo.save(rental);
        } catch (Exception e) {
            throw new RuntimeException("Error while renting instrument: " + e.getMessage());
        }
    }

    @Override
    public List<RentalDto> getAllRentals() {
        List<Rental> rentals = rentalRepo.findAll();
        return rentals.stream().map(rental -> {
            RentalDto rentalDto = new RentalDto();
            rentalDto.setId(rental.getId());
            rentalDto.setRentalDate(rental.getRentalDate());
            rentalDto.setReturnDate(rental.getReturnDate());
            rentalDto.setInstrument(InstrumentDto.toDto(rental.getInstrument()));
            rentalDto.setStatus(rental.getStatus());
            rentalDto.setUser(UserDto.toDto(rental.getUser()));
            return rentalDto;
        }).toList();
    }

    @Override
    public List<RentalDto> getAllRentalsByUser(int id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with email " + id + " not found"));

        List<Rental> rentals = rentalRepo.findAllByUser(user);
        return rentals.stream().map(rental -> {
            RentalDto rentalDto = new RentalDto();
            rentalDto.setId(rental.getId());
            rentalDto.setRentalDate(rental.getRentalDate());
            rentalDto.setReturnDate(rental.getReturnDate());
            rentalDto.setStatus(rental.getStatus());

            rentalDto.setInstrument(InstrumentDto.toDto(rental.getInstrument()));
            rentalDto.setUser(UserDto.toDto(rental.getUser()));
            return rentalDto;
        }).toList();
    }
}
