package com.codecool.brightxchange.repositories.types;

import com.codecool.brightxchange.model.productTypes.GraphicsCardMemoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphicsCardMemoryTypeDAO extends JpaRepository<GraphicsCardMemoryType, Long> {
}