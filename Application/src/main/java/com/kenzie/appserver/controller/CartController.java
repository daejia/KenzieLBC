package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.service.CartService;
import com.kenzie.appserver.service.ItemService;
import com.kenzie.appserver.service.model.Cart;
import com.kenzie.appserver.service.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    private ItemService itemService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> get(@PathVariable("id") String id) {

        Cart cart = cartService.findById(id);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        CartResponse cartResponse = createCartResponse(cart);

        return ResponseEntity.ok(cartResponse);
    }

    @GetMapping()
    public ResponseEntity<List<ItemResponse>> getAllCartItems() {
        List<Item> items = itemService.findAllItems();
        // If there are no concerts, then return a 204
        if (items == null || items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Otherwise, convert the List of Concert objects into a List of ConcertResponses and return it
        List<ItemResponse> response = new ArrayList<>();
        for (Item item : items) {
            response.add(createItemResponse(item));
        }
        return ResponseEntity.ok(response);
    }



    @PostMapping
    public ResponseEntity<CartResponse> addNewCart(@RequestBody CartCreateRequest cartCreateRequest) {
        Cart cart = new Cart(randomUUID().toString(), cartCreateRequest.getUser(), cartCreateRequest.getItems(), cartCreateRequest.getIsInStock());
        cartService.addNewCart(cart);

        CartResponse cartResponse = createCartResponse(cart);

        return ResponseEntity.created(URI.create("/cart/" + cartResponse.getId())).body(cartResponse);
    }

    @PutMapping
    public ResponseEntity<CartResponse> updateCart(@RequestBody CartUpdateRequest cartUpdateRequest) {
        Cart cart = new Cart(cartUpdateRequest.getId(), cartUpdateRequest.getUser(), cartUpdateRequest.getItems(),
                cartUpdateRequest.getIsInStock());
        cartService.updateCart(cart);

        CartResponse cartResponse = createCartResponse(cart);

        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CartResponse> deleteCart(@PathVariable("id") String id) {
        Cart cart = cartService.findById(id);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        cartService.deleteCart(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private CartResponse createCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setUser(cart.getUser());
        cartResponse.setItems(cart.getItems());
        return cartResponse;
    }
    private ItemResponse createItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(item.getId());
        itemResponse.setStore(item.getStore());
        itemResponse.setBrandType(item.getBrandType());
        itemResponse.setName(item.getName());
        itemResponse.setCategory(item.getCategory());
        itemResponse.setPrice(item.getPrice());
        itemResponse.setIsInStock(item.getIsInStock());
        return itemResponse;
    }
}
