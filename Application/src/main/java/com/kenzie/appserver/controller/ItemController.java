package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.ItemCreateRequest;
import com.kenzie.appserver.controller.model.ItemResponse;
import com.kenzie.appserver.service.ItemService;
import com.kenzie.appserver.service.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/cart")
public class ItemController {
    ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> addNewItem(@RequestBody ItemCreateRequest itemCreateRequest) {
        Item item = new Item(randomUUID().toString(),
                itemCreateRequest.getStore(), itemCreateRequest.getBrandType(), itemCreateRequest.getName(),
                itemCreateRequest.getCategory(), itemCreateRequest.getPrice(), itemCreateRequest.getIsInStock());
        itemService.addNewItem(item);

        ItemResponse itemResponse = createItemResponse(item);

        return ResponseEntity.created(URI.create("/cart/" + itemResponse.getId())).body(itemResponse);
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
