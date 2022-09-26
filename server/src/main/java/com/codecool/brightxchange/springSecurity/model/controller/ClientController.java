package com.codecool.brightxchange.springSecurity.model.controller;

import com.codecool.brightxchange.springSecurity.entity.Client;
import com.codecool.brightxchange.springSecurity.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientRepository clients;


    @Autowired
    public ClientController(ClientRepository clients) {
        this.clients = clients;
    }

    @GetMapping("/get/{clientId}")
    public ResponseEntity<Client> getClient(@PathVariable Long clientId) {
        return new ResponseEntity<>(clients.getClientById(clientId), HttpStatus.OK);
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity<Object> isEmailAlreadyRegistered(@PathVariable String email) {
        if (clients.existsClientsByEmail(email))
            return new ResponseEntity<>("Already registered", HttpStatus.ALREADY_REPORTED);

        return new ResponseEntity<>("Accepted", HttpStatus.OK);
    }
}
