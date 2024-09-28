package com.hymns.hymns.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "payment")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id

    @SequenceGenerator(
            name = "instrument_sequence",
            sequenceName = "instrument_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "instrument_sequence"
    )
    private int paymentId;

    @Column(name = "payment_amount")
    private double paymentAmount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_status")
    private String paymentStatus;

    @ManyToOne
    private User user;


    @ManyToOne
    private Rental rental;


    @Column(name = "tidx")
    private String tidx;

    @Column(name = "pidx")
    private String pidx;

}
