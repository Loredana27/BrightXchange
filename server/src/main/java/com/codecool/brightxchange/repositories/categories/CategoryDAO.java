package com.codecool.brightxchange.repositories.categories;

import com.codecool.brightxchange.model.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends JpaRepository<Category,Long> {
}
