package com.codecool.brightxchange.service;

import com.codecool.brightxchange.model.CartItem;
import com.codecool.brightxchange.repositories.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }


    public void updateCartItemForAddButton(CartItem cartItem){
        Optional<CartItem> cartItemOptional = cartItemRepository.findByClientIdAndProductId(cartItem.getProduct().getId(), cartItem.getClient().getId());
        if (cartItemOptional.isPresent()){
            if(cartItemOptional.get().getProduct().getQuantity() >= cartItemOptional.get().getQuantity()+1)
                cartItemRepository.updateCartItem(cartItem.getProduct().getId(), cartItem.getClient().getId(), cartItemOptional.get().getQuantity()+1);
            else
                cartItemRepository.updateCartItem(cartItem.getProduct().getId(), cartItem.getClient().getId(), cartItemOptional.get().getProduct().getQuantity() );
        }
        else cartItemRepository.save(cartItem);
    }

    public List<CartItem> getAllByClientId(Long clientId){
        return cartItemRepository.findAllByClientId(clientId);
    }
}
