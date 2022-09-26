package com.codecool.brightxchange.repositories;

import com.codecool.brightxchange.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findTopByOrderByIdDesc();

    Category findCategoryByNameEquals(String name);

    @Query("SELECT c FROM Category c WHERE  c.name like ?1 ")
    List<Category> findAll(String searchText);



}
