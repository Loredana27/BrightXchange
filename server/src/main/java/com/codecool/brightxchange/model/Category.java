package com.codecool.brightxchange.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "category_name_unique", columnNames = "name")
        }
)
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    @JsonProperty("id")
    private Long id;

    @Column(
            name = "name",
            columnDefinition = "TEXT"
    )
    @JsonProperty("name")
    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    @JsonProperty("specs")
    @Builder.Default
    private List<CategorySpec> categorySpecs = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonProperty("image")
    private CategoryImage image;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categorySpecs=" + categorySpecs +
                ", image=" + image +
                '}';
    }

    public String getImageName() {
        return String.format("category_%s_%s", id, name);
    }
}
