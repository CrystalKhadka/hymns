package com.hymns.hymns.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstrumentDto {

    private int instrumentId;


    private String instrumentName;


    private String instrumentType;

    private double instrumentRentalPrice;


    private String instrumentRentalStatus;


    private String instrumentImage;


    private String instrumentCondition;


}
