package com.codecool.brightxchange.springSecurity.repository;

import com.codecool.brightxchange.springSecurity.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    Client getClientById(Long id);

    boolean existsClientsByEmail(String email);
}
