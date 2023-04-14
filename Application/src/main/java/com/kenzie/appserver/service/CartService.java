package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CartRepository;
import com.kenzie.appserver.repositories.model.CartRecord;
import com.kenzie.appserver.service.model.Cart;
import org.springframework.stereotype.Service;



@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart findById(String id) {
        Cart cartFromBackend = cartRepository
                .findById(id)
                .map(cart -> new Cart(cart.getId(), cart.getUser(), cart.getItems(), cart.getIsInStock()))
                .orElse(null);

        return cartFromBackend;
    }

    public Cart addNewCart(Cart cart) {
        CartRecord cartRecord = new CartRecord();
        cartRecord.setId(cart.getId());
        cartRecord.setUser(cart.getUser());
        cartRecord.setItems(cart.getItems());
        cartRepository.save(cartRecord);
        return cart;
    }

    public Cart updateCart(Cart cart) {
        CartRecord cartRecord = new CartRecord();
        cartRecord.setId(cart.getId());
        cartRecord.setUser(cart.getUser());
        cartRecord.setItems(cart.getItems());
        cartRepository.save(cartRecord);
        return cart;
    }

    public void deleteCart(String id) {
        cartRepository.deleteById(id);
    }

}
