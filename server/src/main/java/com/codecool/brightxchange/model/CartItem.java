package com.codecool.brightxchange.model;

import com.codecool.brightxchange.springSecurity.entity.Client;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CartItem {
    @Id
    @SequenceGenerator(
            name = "cart_item_sequence",
            sequenceName = "cart_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cart_item_sequence"
    )
    @JsonProperty("id")
    private Long id;
    @ManyToOne
    private Client client;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Product product;
    private Integer quantity;
    @Builder.Default
    private Boolean ordered=Boolean.FALSE;
}
