package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CartRepository;
import com.kenzie.appserver.repositories.model.CartRecord;
import com.kenzie.appserver.service.model.Cart;
import com.kenzie.appserver.service.model.Item;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart findById(String id) {
        Cart cartFromBackend = cartRepository
                .findById(id)
                .map(cart -> new Cart(cart.getId(), cart.getUser(), (Map<Item, Integer>) cart.getItems()))
                .orElse(null);

        return cartFromBackend;
    }

    public Cart addNewCart(Cart cart) {
        CartRecord cartRecord = new CartRecord();
        cartRecord.setId(cart.getId());
        cartRecord.setUser(cart.getUser());
        cartRecord.setItems((Map<Item, Integer>) cart.getItems());
        cartRepository.save(cartRecord);
        return cart;
    }
}
