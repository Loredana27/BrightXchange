package com.codecool.brightxchange.repositories;

import com.codecool.brightxchange.model.CategoryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryImageRepository extends JpaRepository<CategoryImage, Long> {
    CategoryImage findTopByOrderByIdDesc();
}
