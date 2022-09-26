package com.codecool.brightxchange.springSecurity.security;

import com.codecool.brightxchange.springSecurity.entity.Client;
import com.codecool.brightxchange.springSecurity.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomClientDetailsService implements UserDetailsService {

    private final ClientRepository clients;

    @Autowired
    public CustomClientDetailsService(ClientRepository clients) {
        this.clients = clients;
    }

    /**
     * Loads the user from the DB and converts it to Spring Security's internal User o object
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clients.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + email + " not found"));

        return new User(client.getEmail(), client.getPassword(),
                client.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
