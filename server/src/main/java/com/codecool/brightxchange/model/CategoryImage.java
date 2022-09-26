package com.codecool.brightxchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CategoryImage {
    @Id
    @SequenceGenerator(
            name = "category_image_sequence",
            sequenceName = "category_image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_image_sequence"
    )
    @JsonProperty("id")
    private Long id;


    @Column(
            columnDefinition = "TEXT"
    )
    @JsonProperty("imageUrl")
    private String imageUrl;

    public CategoryImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CategoryImage{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
