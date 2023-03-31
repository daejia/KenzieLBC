package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CartRepository;
import com.kenzie.appserver.repositories.model.CartRecord;
import com.kenzie.appserver.service.model.Cart;
import com.kenzie.appserver.service.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .map(cart -> new Cart(cart.getId(), cart.getUser(), cart.getItems()))
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

    public List<Cart> getAllCartItems(Long cartId) throws CartNotFoundException {
        List<Cart> items = cartRepository.findByCartId(cartId);

        if (items.isEmpty()) {
            throw new CartService.CartNotFoundException("Cart not found");
        }
        return items;
    }

//    public Item getCartItem(Long cartId, String item) throws CartNotFoundException {
//        List<Cart> cartsList = cartRepository.findByCartId(cartId);
//
//        if (cartsList.isEmpty()) {
//            throw new CartNotFoundException("Can not retrieve item, cart does not exist!");
//        }
//
//        for (Cart cart : cartsList) {
//            if (cart.getItems().containsKey(item)) {
//                Item foundItem = cart.getItems().get(item);
//            }
//        }
//        return foundItem;
//    }

    public class CartNotFoundException extends Throwable {
        public CartNotFoundException(String message) {
            super(message);
        }
    }
}
