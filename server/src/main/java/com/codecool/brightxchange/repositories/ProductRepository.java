package com.codecool.brightxchange.repositories;

import com.codecool.brightxchange.model.Category;
import com.codecool.brightxchange.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getAllByCategoryEquals(Category category);

    @Modifying
    @Query("update Product p set p.quantity= ?2 where p.id= ?1 ")
    void updateNumberOfProducts(Long productId,int quantity);

    @Query("select p from Product p where lower(p.name) like lower(?1)")
    List<Product> findAllProductsByName(String searchText);

    @Query("select p from Product p where lower(p.name) like lower(?1) and p.price >= ?2 and p.price<= ?3 and p.producer.name in ?4 and p.category.name in ?5 ")
    List<Product> findAllSimpleFiltered(String searchText,
                                        Double minPrice,
                                        Double maxPrice,
                                        List<String> producers,
                                        List<String> categories);
}
