package com.codecool.brightxchange.service;

import com.codecool.brightxchange.model.Order;
import com.codecool.brightxchange.repositories.CartItemRepository;
import com.codecool.brightxchange.repositories.OrderRepository;
import com.codecool.brightxchange.springSecurity.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.clientRepository = clientRepository;
    }

    public Optional<Order> getById(Long id){
        return orderRepository.findById(id);
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public Order getUnfinishedOrderByClientId(Long clientId) {
        if(orderRepository.findUnfinishedByClientId(clientId).isPresent()){
            Order order = orderRepository.getUnfinishedByClientId(clientId);
            order.setProducts(cartItemRepository.findAllByClientId(clientId));
            return order;
        }
        Order order = new Order();
        order.setClient(clientRepository.getClientById(clientId));
        order.setProducts(cartItemRepository.findAllByClientId(clientId));
        order.setPrice(
                order.getProducts().stream().map(c -> c.getProduct().getPrice()*c.getQuantity()).reduce(Double::sum).orElse(0d)
        );
        order.setMethod("paypal");
        return order;
    }
}
