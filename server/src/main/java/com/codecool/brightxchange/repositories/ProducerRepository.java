package com.codecool.brightxchange.repositories;

import com.codecool.brightxchange.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Producer findProducerByNameEquals(String name);

    Optional<Producer> findByName(String name);
}
