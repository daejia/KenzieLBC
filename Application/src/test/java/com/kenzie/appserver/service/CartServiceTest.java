package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CartRepository;

import com.kenzie.appserver.repositories.StoreRepository;
import com.kenzie.appserver.repositories.model.CartRecord;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CartServiceTest {
    private CartRepository cartRepository;
    private CartService cartService;


    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        cartService = new CartService(cartRepository);
    }

    @Test

    void findById_validId_returnsCart(){
        //GIVEN
        String id = randomUUID().toString();
        String user = "user";
        Map<Item, Integer> items = new HashMap<>();
        Cart cart = new Cart(id, user, items);

        CartRecord cartRecord = new CartRecord();
        cartRecord.setId(cart.getId());
        cartRecord.setUser(cart.getUser());
        cartRecord.setItems(cart.getItems());
        cartRepository.save(cartRecord);

        //WHEN
        when(cartRepository.findById(id)).thenReturn(Optional.of(cartRecord));
        Cart foundCart = cartService.findById(id);

        //THEN
        assertNotNull(foundCart, "The cart was returned");
        assertEquals(cartRecord.getId(), foundCart.getId(), "The cart Id matches");
        assertEquals(cartRecord.getUser(), foundCart.getUser(), "The users match");
        assertEquals(cartRecord.getItems(), foundCart.getItems(), "The items match");
    }

    @Test
    void findById_invalidId_returnsNull() {
        //GIVEN
        String cartId = randomUUID().toString();

        //WHEN
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());
        Cart cart = cartService.findById(cartId);

        //THEN
        assertNull(cart, "The cart is null");
    }

    @Test
    void addNewCart_validCart_returnsCart() {
        //GIVEN
        String id = randomUUID().toString();
        String user = "user";
        Map<Item, Integer> items = new HashMap<>();
        Cart cart = new Cart(id, user, items);

        CartRecord cartRecord = new CartRecord();
        cartRecord.setId(cart.getId());
        cartRecord.setUser(cart.getUser());
        cartRecord.setItems(cart.getItems());
        cartRepository.save(cartRecord);

        //WHEN
        when(cartRepository.save(cartRecord)).thenReturn(cartRecord);
        Cart addedCart = cartService.addNewCart(cart);

        //THEN
        assertNotNull(addedCart, "The Cart is returned");
        assertEquals(cartRecord.getId(), addedCart.getId(), "The id matches");
        assertEquals(cartRecord.getUser(), addedCart.getUser(), "The users match");
        assertEquals(cartRecord.getItems(), addedCart.getItems(), "The items match");

    public void testGetAllCartItems() throws CartService.CartNotFoundException {
        String id = UUID.randomUUID().toString();
        String user = "Maya";
        Map<String, Item> items = new HashMap<>();
        Store wholeMoods = new Store(UUID.randomUUID().toString(), "wholeMoods", "423 WallabyWay", "Sydney", "Australia", "66666", true);
        Item television = new Item(UUID.randomUUID().toString(), wholeMoods, BrandType.NAME_BRAND, "television", Category.ELECTRONIC, 17.38, true);
        Item boomBox = new Item(UUID.randomUUID().toString(), wholeMoods, BrandType.NAME_BRAND, "boomBox", Category.ELECTRONIC, 11, true);
        items.put("television", television);
        items.put("boomBox", boomBox);
        Cart cart = new Cart(id, user, items);
        CartRecord cartRecord = new CartRecord();
        cartRecord.setItems(items);
        cartRecord.setId(id);
        cartRecord.setUser(user);
        Mockito.when(cartRepository.findById(id)).thenReturn(Optional.of(cartRecord));

        List<Item> cartItems = cartService.getAllCartItems(id);

        assertEquals(2, cartItems.size());
        assertTrue(cartItems.contains(television));
        assertTrue(cartItems.contains(boomBox));
    }
}
