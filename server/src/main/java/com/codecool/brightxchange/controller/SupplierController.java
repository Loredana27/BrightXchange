package com.codecool.brightxchange.controller;

import com.codecool.brightxchange.model.Supplier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @GetMapping
    public List<Supplier> getAll(){
        return null;
    }

    @GetMapping("{id}")
    public Supplier getById(@PathVariable("id") Long id){
        return null;
    }
}