package com.codecool.brightxchange.controller.productController;

import com.codecool.brightxchange.model.CartItem;
import com.codecool.brightxchange.model.Product;
import com.codecool.brightxchange.service.CartItemService;
import com.codecool.brightxchange.service.ProductService;
import com.codecool.brightxchange.springSecurity.entity.Client;
import com.codecool.brightxchange.springSecurity.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/cart-item")
public class CartItemController {
    private final CartItemService  cartItemService;
    private  final ProductService productService;
    private final ClientRepository clientRepository;


    public CartItemController(CartItemService cartItemService, ProductService productService, ClientRepository clientRepository) {
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.clientRepository = clientRepository;
    }

    @PostMapping
    private ResponseEntity<String> addToCart(@RequestBody CartItem cartItem){
        Optional<Product> productById = productService.getProductById(cartItem.getProduct().getId());
        Product product;
        if (productById.isPresent()){
            product = productById.get();
            if (product.getQuantity()<cartItem.getQuantity()){
                return new ResponseEntity<>("Product quantity is not enough",HttpStatus.BAD_REQUEST);
            }
        }
        else return new ResponseEntity<>("Product not existent",HttpStatus.BAD_REQUEST);
        Client clientById = clientRepository.getClientById(cartItem.getClient().getId());
        cartItem.setProduct(product);
        cartItem.setClient(clientById);
        cartItemService.updateCartItemForAddButton(cartItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestBody CartItem cartItem){
        cartItemService.updateCartItem(cartItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public List<CartItem> getAll(@PathVariable Long id){
        return cartItemService.getAllByClientId(id);
    }
}
