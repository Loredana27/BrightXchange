package com.codecool.brightxchange.repositories;

import com.codecool.brightxchange.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c WHERE c.client.id = ?2 AND c.product.id = ?1 and c.ordered=false")
    Optional<CartItem> findByClientIdAndProductId(Long productId, Long clientId);

    @Modifying
    @Transactional
    @Query("update CartItem c set c.quantity = ?3 where c.product.id = ?1 and c.client.id = ?2 and c.ordered=false")
    void updateCartItem(Long productId, Long clientId, Integer quantity);
    @Query("SELECT c FROM CartItem c WHERE c.client.id = ?1 and c.ordered=false and c.quantity>0")
    List<CartItem> findAllByClientId(Long clientId);
}
