package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.StoreRepository;
import com.kenzie.appserver.repositories.model.StoreRecord;
import com.kenzie.appserver.service.model.Store;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {
    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store findById(String id) {
        Store storeFromBackend = storeRepository
                .findById(id)
                .map(store -> new Store(store.getId(), store.getName(), store.getAddress(), store.getCity(),
                        store.getState(), store.getZip()))
                .orElse(null);

        return storeFromBackend;
    }

    public Store addNewStore(Store store) {
        StoreRecord storeRecord = new StoreRecord();
        storeRecord.setId(store.getId());
        storeRecord.setName(store.getName());
        storeRecord.setAddress(store.getAddress());
        storeRecord.setCity(store.getCity());
        storeRecord.setState(store.getState());
        storeRecord.setZip(store.getZip());
//        storeRecord.setIsInRadius(store.isInRadius());
        storeRepository.save(storeRecord);
        return store;
    }

    public List<Store> findAllStores() {
        List<Store> stores = new ArrayList<>();

        Iterable<StoreRecord> storeIterator = storeRepository.findAll();
        for (StoreRecord record : storeIterator) {
            stores.add(new Store(record.getId(),
                    record.getName(),
                    record.getAddress(),
                    record.getCity(),
                    record.getState(),
                    record.getZip()));
        }

        return stores;
    }
}
