package com.hymns.hymns.dto;

import lombok.*;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentalDto {

    private Long id;

    private int instrument;

    private int user;

    private Date rentalDate;

    private Date returnDate;
}
