package com.hymns.hymns.service.impl;

import com.hymns.hymns.dto.RentalDto;
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

    private final RentalRepo instrumentRepository;
    private final JWTService jwtService;
    private final UserRepo userRepo;
    private final InstrumentRepo instrumentRepo;
    private final RentalRepo rentalRepo;


    @Override
    public void rentInstrument(RentalDto rentalDto) {
        Rental rental = new Rental();
        User user = userRepo.findById(rentalDto.getUser()).orElseThrow(() -> new RuntimeException("User not found"));
        rental.setUser(user);
        Instrument instrument = instrumentRepo.findById(rentalDto.getInstrument()).orElseThrow(() -> new RuntimeException("Instrument not found"));
        rental.setRentalDate(rentalDto.getRentalDate());
        rental.setInstrument(instrument);
        rental.setReturnDate(rentalDto.getReturnDate());

        instrument.setInstrumentRentalStatus("Rented");
        instrumentRepo.save(instrument);

        rentalRepo.save(rental);
    }

    @Override
    public List<RentalDto> getAllRentals() {
        List<Rental> rentals = rentalRepo.findAll();
        return rentals.stream().map(rental -> {
            RentalDto rentalDto = new RentalDto();
            rentalDto.setRentalDate(rental.getRentalDate());
            rentalDto.setReturnDate(rental.getReturnDate());
            rentalDto.setInstrument(rental.getInstrument().getInstrumentId());
            rentalDto.setUser(rental.getUser().getId());
            return rentalDto;
        }).toList();
    }

    @Override
    public List<RentalDto> getAllRentalsByUser(String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<Rental> rentals = rentalRepo.findAllByUser(user.getId());
        return rentals.stream().map(rental -> {
            RentalDto rentalDto = new RentalDto();
            rentalDto.setRentalDate(rental.getRentalDate());
            rentalDto.setReturnDate(rental.getReturnDate());
            rentalDto.setInstrument(rental.getInstrument().getInstrumentId());
            rentalDto.setUser(rental.getUser().getId());
            return rentalDto;
        }).toList();
    }
}
