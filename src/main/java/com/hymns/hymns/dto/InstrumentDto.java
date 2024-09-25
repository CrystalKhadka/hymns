package com.hymns.hymns.dto;

import com.hymns.hymns.entity.Instrument;
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

    private boolean added;

    private boolean addedByUser;

    //    to dto
    public static InstrumentDto toDto(Instrument instrument) {
        return InstrumentDto.builder()
                .instrumentId(instrument.getInstrumentId())
                .instrumentName(instrument.getInstrumentName())
                .instrumentType(instrument.getInstrumentType())
                .instrumentRentalPrice(instrument.getInstrumentRentalPrice())
                .instrumentRentalStatus(instrument.getInstrumentRentalStatus())
                .instrumentImage(instrument.getInstrumentImage())
                .instrumentCondition(instrument.getInstrumentCondition())
                .added(instrument.isAdded())
                .build();
    }


}
