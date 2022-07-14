package com.codecool.brightxchange.repositories.products;

import com.codecool.brightxchange.model.products.Cooler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoolerDAO extends JpaRepository<Cooler, Long> {
}