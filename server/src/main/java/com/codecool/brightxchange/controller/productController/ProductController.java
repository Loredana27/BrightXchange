package com.codecool.brightxchange.controller.productController;

import com.codecool.brightxchange.model.Category;
import com.codecool.brightxchange.model.Product;
import com.codecool.brightxchange.service.CategoryService;
import com.codecool.brightxchange.service.ProductService;
import com.codecool.brightxchange.model.SimpleFilter;
import com.codecool.brightxchange.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProducerService producerService;


    public ProductController(ProductService productService, CategoryService categoryService, ProducerService producerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.producerService = producerService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("{id}")
    public Product getOneProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id).orElse(null);
    }


    @GetMapping("/category/{categoryName}")
    public List<Product> getByCategoryName(@PathVariable("categoryName") String categoryName) {
        Category category = categoryService.getByName(categoryName);
        return productService.getByCategory(category);
    }


    @GetMapping("/search/{searchText}")
    public ResponseEntity<List<Product>> getALLProductsByName(@PathVariable String searchText){
        return new ResponseEntity<>(productService.findAllProductsByName(searchText), HttpStatus.OK);
    }


    @PostMapping("/search")
    public List<Product> findAllSimpleFiltered(@RequestBody SimpleFilter simpleFilter){
        System.out.println(simpleFilter);
        if (simpleFilter.getProducers().isEmpty()) simpleFilter.setProducers(producerService.getAllNames());
        if (simpleFilter.getCategories().isEmpty()) simpleFilter.setCategories(categoryService.getAllNames());
        return productService.findAllSimpleFiltered(simpleFilter.getSearchText(),
                simpleFilter.getMinPrice(),
                simpleFilter.getMaxPrice(),
                simpleFilter.getProducers(),
                simpleFilter.getCategories());
    }



}
