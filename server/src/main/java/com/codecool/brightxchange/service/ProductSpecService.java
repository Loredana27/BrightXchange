package com.codecool.brightxchange.service;

import com.codecool.brightxchange.model.ProductSpec;
import com.codecool.brightxchange.repositories.ProductSpecRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecService {
    private final ProductSpecRepository repository;

    public ProductSpecService(ProductSpecRepository repository) {
        this.repository = repository;
    }

    public void addProductSpec(ProductSpec productSpec) {
        repository.save(productSpec);
    }

    public void addAllProductSpec(List<ProductSpec> productSpecList) {
        repository.saveAll(productSpecList);
    }

    public List<ProductSpec> addProduct(List<ProductSpec> productSpecs) {
        return repository.saveAllAndFlush(productSpecs);
    }
}
