package com.codecool.brightxchange.repositories;

import com.codecool.brightxchange.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.finished=false and o.client.id=?1")
    Optional<Order> findUnfinishedByClientId(Long clientId);

    @Query("select o from Order o where o.finished=false and o.client.id=?1")
    Order getUnfinishedByClientId(Long clientId);
}
