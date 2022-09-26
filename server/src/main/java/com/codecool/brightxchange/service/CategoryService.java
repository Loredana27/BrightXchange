package com.codecool.brightxchange.service;

import com.codecool.brightxchange.model.Category;
import com.codecool.brightxchange.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> selectAll() {
        return repository.findAll();
    }

    public void save(Category category) {
        repository.save(category);
    }

    public Category saveAndFlush(Category category) {
        return repository.saveAndFlush(category);
    }

    public Category selectLast() {
        return repository.findTopByOrderByIdDesc();
    }

    public Optional<Category> getOne(Long id) {
        return repository.findById(id);
    }

    public Category getByName(String categoryName) {
        return repository.findCategoryByNameEquals(categoryName);
    }

    public List<Category>  findAll(String searchText){
        return repository.findAll("%"+searchText+"%");
    }

    public List<String> getAllNames() {
        return repository.findAll().stream().map(Category::getName).collect(Collectors.toList());
    }
}
