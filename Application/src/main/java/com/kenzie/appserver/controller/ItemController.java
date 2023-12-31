package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.CartResponse;
import com.kenzie.appserver.controller.model.ItemCreateRequest;
import com.kenzie.appserver.controller.model.ItemResponse;
import com.kenzie.appserver.controller.model.ItemUpdateRequest;
import com.kenzie.appserver.service.ItemService;
import com.kenzie.appserver.service.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/item")
public class ItemController {
    ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getCartItem(@PathVariable("id") String id) {

        Item item = itemService.findById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        ItemResponse itemResponse = createItemResponse(item);

        return ResponseEntity.ok(itemResponse);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> addNewItem(@RequestBody ItemCreateRequest itemCreateRequest) {
        Item item = new Item(randomUUID().toString(),
                itemCreateRequest.getStore(), itemCreateRequest.getBrandType(), itemCreateRequest.getName(),
                itemCreateRequest.getCategory(), itemCreateRequest.getPrice(), itemCreateRequest.getIsInStock());
        itemService.addNewItem(item);

        ItemResponse itemResponse = createItemResponse(item);
        return ResponseEntity.created(URI.create("/item/" + itemResponse.getId())).body(itemResponse);
    }

    @PutMapping
    public ResponseEntity<ItemResponse> updateItem(@RequestBody ItemUpdateRequest itemUpdateRequest) {
        Item item = new Item(itemUpdateRequest.getId(), itemUpdateRequest.getStore(), itemUpdateRequest.getBrandType(),
                itemUpdateRequest.getName(), itemUpdateRequest.getCategory(), itemUpdateRequest.getPrice(),
                itemUpdateRequest.getIsInStock());
        itemService.updateItem(item);

        ItemResponse itemResponse = createItemResponse(item);

        return ResponseEntity.ok(itemResponse);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        List<Item> items = ItemService.findAllItems();
        if (items == null || items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ItemResponse> response = new ArrayList<>();
        for (Item item : items) {
            response.add(this.createItemResponse(item));
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ItemResponse> deleteItem(@PathVariable("id") String id) {
        Item item = itemService.findById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    private ItemResponse createItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(item.getId());
        itemResponse.setStore(item.getStore());
        itemResponse.setName(item.getName());
        itemResponse.setBrandType(item.getBrandType());
        itemResponse.setCategory(item.getCategory());
        itemResponse.setPrice(item.getPrice());
        itemResponse.setIsInStock(item.getIsInStock());
        return itemResponse;
    }


}
