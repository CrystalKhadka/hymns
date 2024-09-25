package com.hymns.hymns.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "rental")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Rental {

    @Id
    @SequenceGenerator(
            name = "rental_sequence",
            sequenceName = "rental_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rental_sequence"
    )
    private Long id;

    @ManyToOne
    private Instrument instrument;

    @ManyToOne
    private User user;

    private Date rentalDate;

    private Date returnDate;

}
