package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.StoreService;
import com.kenzie.appserver.controller.model.StoreCreateRequest;
import com.kenzie.appserver.controller.model.StoreResponse;
import com.kenzie.appserver.service.model.Store;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/store")
public class StoreController {
    private StoreService storeService;

    StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable("id") String id) {

        Store store = storeService.findById(id);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }

        StoreResponse storeResponse = createStoreResponse(store);
        return ResponseEntity.ok(storeResponse);
    }

    @GetMapping
    public ResponseEntity<List<StoreResponse>> getAllStores() {
        List<Store> stores = storeService.findAllStores();
        if (stores == null || stores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<StoreResponse> response = new ArrayList<>();
        for (Store store : stores) {
            response.add(this.createStoreResponse(store));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<StoreResponse> addNewStore(@RequestBody StoreCreateRequest storeCreateRequest) {
        Store store = new Store(randomUUID().toString(),
                storeCreateRequest.getName(), storeCreateRequest.getAddress(), storeCreateRequest.getCity(),
                storeCreateRequest.getState(), storeCreateRequest.getZip(), storeCreateRequest.getIsInRadius());
        storeService.addNewStore(store);

        StoreResponse storeResponse = createStoreResponse(store);

        return ResponseEntity.created(URI.create("/store/" + storeResponse.getId())).body(storeResponse);
    }

    private StoreResponse createStoreResponse(Store store) {
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setId(store.getId());
        storeResponse.setName(store.getName());
        storeResponse.setAddress(store.getAddress());
        storeResponse.setCity(store.getCity());
        storeResponse.setState(store.getState());
        storeResponse.setZip(store.getZip());
        storeResponse.setIsInRadius(store.isInRadius());
        return storeResponse;
    }
}
