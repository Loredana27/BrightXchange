package com.codecool.brightxchange.springSecurity.model.controller;

import com.codecool.brightxchange.springSecurity.entity.Client;
import com.codecool.brightxchange.springSecurity.model.ClientCredentials;
import com.codecool.brightxchange.springSecurity.repository.ClientRepository;
import com.codecool.brightxchange.springSecurity.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    private final ClientRepository clients;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices, ClientRepository clients) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
        this.clients = clients;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody ClientCredentials data) {
        try {
            String email = data.getEmail();
            Client client = clients.findByEmail(email).orElseThrow(() -> {
                throw new UsernameNotFoundException("");
            });
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            String token = jwtTokenServices.createToken(email, roles);
            Map<Object, Object> model = new HashMap<>();
            model.put("id", client.getId());
            model.put("name", String.format("%s %s", client.getFirstName(), client.getLastName()));
            model.put("roles", roles);
            model.put("token", String.format("Bearer %s", token));
//            model.put("status", 200);
            return ResponseEntity.ok(model);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Acest e-mail nu exista!", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Parola incorecta!", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Informatii incorecte!", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }

    @PostMapping("/register")
    ResponseEntity<Object> registerClient(@RequestBody Client client) {
        client.setRoles(List.of("USER"));
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clients.save(client);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

}
