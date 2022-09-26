package com.codecool.brightxchange.model;


import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class ProductSpec {
    @Id
    @SequenceGenerator(
            name = "supplier_sequence",
            sequenceName = "supplier_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "supplier_sequence"
    )
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    private CategorySpec categorySpec;

    @Column(
            columnDefinition = "TEXT"
    )
    private String stringValue;
    private Long numberValue;
}
