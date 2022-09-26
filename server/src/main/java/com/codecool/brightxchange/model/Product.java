package com.codecool.brightxchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    private String name;

    private Integer quantity;

    private Double price;
    @JsonProperty("specs")
    @OneToMany(cascade = {CascadeType.ALL})
    private List<ProductSpec> productSpecList;
    @OneToMany(cascade = {CascadeType.ALL})
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private Producer producer;

    @JsonProperty("category")
    @ManyToOne
    @JoinColumn
    private Category category;


    public List<String> getImageNames() {
        List<String> imageName = new ArrayList<>();
        for (ProductImage image : images) {
            imageName.add(image.getImageUrl());
        }
        return imageName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", productSpecList=" + productSpecList +
                ", images=" + images +
                ", producer=" + producer +
                ", category=" + category +
                '}';
    }

    public String getNameForImages() {
        return String.format("product_%s_%s", id, name);
    }
}
