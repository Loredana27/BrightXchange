package com.codecool.brightxchange.service;

import com.codecool.brightxchange.model.ProductImage;
import com.codecool.brightxchange.repositories.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    private final ProductImageRepository repository;

    public ProductImageService(ProductImageRepository repository) {
        this.repository = repository;
    }

    public void addProductImage(ProductImage productImage) {
        repository.save(productImage);
    }

    public Optional<ProductImage> getOne(Long id){
        return repository.findById(id);
    }

    public List<ProductImage> saveAndFlush(List<ProductImage> productImage) {
        return repository.saveAllAndFlush(productImage);
    }

    public ProductImage saveAnImage(ProductImage productImage) {
        return repository.saveAndFlush(productImage);
    }

    public List<ProductImage> getAllImages() {
        return repository.findAll();
    }

    public List<ProductImage> selectAll(){return repository.findAll();}
}
