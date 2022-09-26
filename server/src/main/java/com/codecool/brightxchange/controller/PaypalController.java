package com.codecool.brightxchange.controller;

import com.codecool.brightxchange.model.Address;
import com.codecool.brightxchange.model.CartItem;
import com.codecool.brightxchange.model.Order;
import com.codecool.brightxchange.model.Product;
import com.codecool.brightxchange.service.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user/payment")
public class PaypalController {
    public static final String SUCCESS_URL = "http://localhost:3000/plata/efectuata";
    public static final String CANCEL_URL = "http://localhost:3000/plata/NEefectuata";
    private final PaypalService paypalService;
    private final ProductService productService;
    private final OrderService orderService;
    private final CartItemService cartItemService;
    private final AddressService addressService;

    public PaypalController(PaypalService paypalService, ProductService productService, OrderService orderService, CartItemService cartItemService, AddressService addressService) {
        this.paypalService = paypalService;
        this.productService = productService;
        this.orderService = orderService;
        this.cartItemService = cartItemService;
        this.addressService = addressService;
    }


    @GetMapping("{clientId}")
    public ResponseEntity<String> payment(@PathVariable Long clientId) {
        try {
            Order order = orderService.getUnfinishedOrderByClientId(clientId);
            Payment payment = paypalService.createPayment(order.getPrice() / 4.9, order.getCurrency(), order.getMethod(),
                    CANCEL_URL, SUCCESS_URL);
            System.out.println(payment);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new ResponseEntity<>(link.getHref(), HttpStatus.OK);
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = "{userId}/{payerId}/{paymentId}")
    public ResponseEntity<String> successPay(@PathVariable Long userId, @PathVariable String paymentId, @PathVariable String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            ShippingAddress shippingAddress = payment.getTransactions().get(0).getItemList().getShippingAddress();
            System.out.println(shippingAddress);

            Address address = new Address();
            address.setCity(shippingAddress.getCity());
            address.setState(shippingAddress.getState().isEmpty() ? shippingAddress.getLine2() : shippingAddress.getState());
            address.setCountryCode(shippingAddress.getCountryCode());
            address.setPostalCode(shippingAddress.getPostalCode());
            address.setRecipientName(shippingAddress.getRecipientName());

            address = addressService.saveAndFlush(address);

            if (payment.getState().equals("approved")) {
                Order order = orderService.getUnfinishedOrderByClientId(userId);
                order.setAddress(address);
                orderService.saveOrder(order);
                List<CartItem> items = order.getProducts();
                List<Product> products = new ArrayList<>();
                for (CartItem item : items) {
                    item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());
                    item.setOrdered(Boolean.TRUE);
                    products.add(item.getProduct());
                }
                productService.saveAll(products);
                cartItemService.saveAll(items);
                order.setFinished(Boolean.TRUE);
                orderService.saveOrder(order);
                return new ResponseEntity<>("Plata a fost un succes", HttpStatus.ACCEPTED);
            } else {

                return new ResponseEntity<>("Plata", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("A aparut o eroare!", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

}
