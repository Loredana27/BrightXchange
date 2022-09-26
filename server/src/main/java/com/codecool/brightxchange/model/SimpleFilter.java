package com.codecool.brightxchange.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SimpleFilter {
    private String searchText;
    private Double minPrice;
    private Double maxPrice;
    private List<String> producers;
    private List<String> categories;
}
