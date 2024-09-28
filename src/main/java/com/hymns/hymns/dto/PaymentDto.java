package com.hymns.hymns.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDto {

    private int paymentId;


    private double paymentAmount;

    private String paymentDate;

    private String paymentType;


    private String paymentStatus;


    private UserDto user;


    private RentalDto rental;


    private String tidx;


    private String pidx;
}
