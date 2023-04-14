package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.service.CartNotFoundException;
import com.kenzie.appserver.service.CartService;
import com.kenzie.appserver.service.ExampleService;
import com.kenzie.appserver.service.model.Cart;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.Item;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class CartControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    CartService cartService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {
        String id = UUID.randomUUID().toString();
        String user = mockNeat.strings().valStr();
        Boolean isInStock = true;
        Map<Item, Integer> itemMap = new HashMap<>();

        Cart cart = new Cart(id,user,itemMap, isInStock);
        Cart persistedCart = cartService.addNewCart(cart);

        mvc.perform(get("/cart/{id}", persistedCart.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("user")
                        .value(is(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCartItems_GetSuccessful() throws Exception, CartNotFoundException {
        String id = UUID.randomUUID().toString();
        String user = mockNeat.strings().valStr();
        Boolean isInStock = true;
        Map<Item, Integer> itemMap = new HashMap<>();

        Cart cart = new Cart(id,user,itemMap, isInStock);
        Cart addedCart = cartService.addNewCart(cart);

        Cart secondCart = new Cart(id, mockNeat.strings().valStr(), itemMap, isInStock);
        Cart cart2 = cartService.addNewCart(secondCart);

//        List<Cart> cartList = cartService.getAllCartItems(id);

        mvc.perform(get("/cart/{cartId}/items", addedCart.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(status().isOk());
    }
}
