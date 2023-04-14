package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CartRepository;
import com.kenzie.appserver.repositories.model.CartRecord;
import com.kenzie.appserver.service.model.Cart;
import com.kenzie.appserver.service.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    public List<Item> getAllCartItems(String cartId) throws CartNotFoundException {
        Cart cart = this.findById(cartId);
        if (cart == null){
            throw new CartNotFoundException("Cart not found");
        }
        List<Item> items = new ArrayList<>();
        for (Item item : items){
            items.add(item);
        }
        if (items.isEmpty()) {
            throw new CartNotFoundException("Add items to your cart!");
        }
        return items;
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
