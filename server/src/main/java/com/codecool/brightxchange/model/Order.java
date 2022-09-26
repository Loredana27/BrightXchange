package com.codecool.brightxchange.model;

import com.codecool.brightxchange.springSecurity.entity.Client;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    @JsonProperty("id")
    private Long id;
    @ManyToOne
    private Client client;
    @OneToMany
    @Builder.Default
    private List<CartItem> products = new ArrayList<>();
    @ManyToOne
    private Address address;
    private double price;
    private String currency = "EUR";
    private String method = "paypal";
    @Builder.Default
    private Boolean finished=Boolean.FALSE;

}
