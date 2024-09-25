package com.hymns.hymns.dto;

import com.hymns.hymns.entity.Rental;
import lombok.*;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentalDto {

    private Long id;

    private InstrumentDto instrument;

    private UserDto user;

    private Date rentalDate;

    private Date returnDate;

    private String status;


    //    to dto
    public static RentalDto toDto(Rental rental) {
        return RentalDto.builder()
                .id(rental.getId())
                .instrument(InstrumentDto.toDto(rental.getInstrument()))
                .user(UserDto.toDto(rental.getUser()))
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .status(rental.getStatus())
                .build();
    }
}
