package com.codecool.brightxchange.model;

import com.codecool.brightxchange.springSecurity.entity.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class OrderPaymentDetails {
    @Id
    @SequenceGenerator(
            name = "order_payment_sequence",
            sequenceName = "order_payment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_payment_sequence"
    )
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Client client;

    private String paymentID;

    @Builder.Default
    private Boolean success = Boolean.FALSE;
}
