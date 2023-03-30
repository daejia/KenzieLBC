package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.CartCreateRequest;
import com.kenzie.appserver.controller.model.CartResponse;
import com.kenzie.appserver.service.CartService;
import com.kenzie.appserver.service.model.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;

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
    @GetMapping("/cart/{cartId}/items")
    public List<Cart> getAllCartItems(@PathVariable Long cartId) throws CartService.CartNotFoundException {
        List<Cart> cartItems = cartService.getAllCartItems(cartId);
        return cartItems;
    }

    @PostMapping
    public ResponseEntity<CartResponse> addNewConcert(@RequestBody CartCreateRequest cartCreateRequest) {
        Cart cart = new Cart(randomUUID().toString(),cartCreateRequest.getUser(),cartCreateRequest.getItems());
        cartService.addNewCart(cart);

        CartResponse cartResponse = createCartResponse(cart);

        return ResponseEntity.created(URI.create("/cart/" + cartResponse.getId())).body(cartResponse);
    }
    private CartResponse createCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setUser(cart.getUser());
        cartResponse.setItems(cart.getItems());
        return cartResponse;
    }
}
