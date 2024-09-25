package com.hymns.hymns.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "instruments")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Instrument {
    @Id
    @Column(name = "instrument_id")
    @SequenceGenerator(
            name = "instrument_sequence",
            sequenceName = "instrument_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "instrument_sequence"
    )
    private int instrumentId;

    @Column(name = "instrument_name")
    private String instrumentName;

    @Column(name = "instrument_type")
    private String instrumentType;

    @Column(name = "instrument_rental_price")
    private double instrumentRentalPrice;

    @Column(name = "instrument_rental_status")
    private String instrumentRentalStatus;

    @Column(name = "instrument_image")
    private String instrumentImage;

    @Column(name = "instrument_condition")
    private String instrumentCondition;

    private boolean added;
}
