package com.codecool.brightxchange.service;

import com.codecool.brightxchange.model.Category;
import com.codecool.brightxchange.model.Product;
import com.codecool.brightxchange.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void addProductService(Product product) {
        repository.save(product);

    }

    public void saveAProduct(Product product) {
        repository.save(product);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public List<Product> getByCategory(Category category) {
        return repository.getAllByCategoryEquals(category);
    }

    public void saveAll(List<Product> products){
        repository.saveAll(products);
    }

    public  List<Product> findAllProductsByName(String searchText){
        return repository
            .findAllProductsByName("%"+searchText+"%");}

    public List<Product> findAllSimpleFiltered(String searchText,
                                               Double minPrice,
                                               Double maxPrice,
                                               List<String> producers,
                                               List<String> categories){
        return repository.findAllSimpleFiltered(
                "%"+searchText+"%",
                minPrice,
                maxPrice,
                producers,
                categories);
    }

}
