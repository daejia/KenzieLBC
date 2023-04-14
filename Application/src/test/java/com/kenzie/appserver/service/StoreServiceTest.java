package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.StoreRepository;
import com.kenzie.appserver.repositories.model.StoreRecord;
import com.kenzie.appserver.service.model.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StoreServiceTest {
    private StoreRepository storeRepository;
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        storeRepository = mock(StoreRepository.class);
        storeService = new StoreService(storeRepository);
    }

    @Test
    void findById_idIsValid_returnsStore() {
        //GIVEN
        String id = randomUUID().toString();
        StoreRecord storeRecord = new StoreRecord();
        storeRecord.setId(id);
        storeRecord.setName("storename");
        storeRecord.setAddress("storeaddress");
        storeRecord.setCity("storecity");
        storeRecord.setState("storestate");
        storeRecord.setZip("12345");
        storeRecord.setIsInRadius(true);

        //WHEN
        when(storeRepository.findById(id)).thenReturn(Optional.of(storeRecord));
        Store store = storeService.findById(id);

        //THEN
        assertNotNull(store, "The store is returned");
        assertEquals(storeRecord.getId(), store.getId(), "The id matches");
        assertEquals(storeRecord.getName(), store.getName(), "The name matches");
        assertEquals(storeRecord.getAddress(), store.getAddress(), "The address matches");
        assertEquals(storeRecord.getCity(), store.getCity(), "The city matches");
        assertEquals(storeRecord.getState(), store.getState(), "The state matches");
        assertEquals(storeRecord.getZip(), store.getZip(), "The zip matches");
        assertEquals(storeRecord.isInRadius(), store.isInRadius(), "The the radius matches");
    }

    @Test
    void findById_isInvalid_returnsNull() {
     //GIVEN
        String id = randomUUID().toString();

        //WHEN
        when(storeRepository.findById(id)).thenReturn(Optional.empty());
        Store store = storeService.findById(id);

        //THEN
        assertNull(store, "The store is null when not found");
    }
    @Test
    void findAllStores_isValid_returnsAllStores(){
        //GIVEN
        String id = randomUUID().toString();
        StoreRecord storeRecord = new StoreRecord();
        storeRecord.setId(id);
        storeRecord.setName("storename");
        storeRecord.setAddress("storeaddress");
        storeRecord.setCity("storecity");
        storeRecord.setState("storestate");
        storeRecord.setZip("12345");
        storeRecord.setIsInRadius(true);

        String id2 = randomUUID().toString();
        StoreRecord storeRecord2 = new StoreRecord();
        storeRecord2.setId(id2);
        storeRecord2.setName("storename2");
        storeRecord2.setAddress("storeaddress2");
        storeRecord2.setCity("storecity2");
        storeRecord2.setState("storestate2");
        storeRecord2.setZip("12342");
        storeRecord2.setIsInRadius(true);

        List<StoreRecord> storeRecords = new ArrayList<>();
        storeRecords.add(storeRecord);
        storeRecords.add(storeRecord2);

        //WHEN

        when(storeRepository.findAll()).thenReturn(storeRecords);
        List<Store> stores = storeService.findAllStores();

        //THEN
        assertNotNull(stores, "The stores are returned");
        assertEquals(storeRecords.size(), stores.size(), "The size of the list matches");
    }
    @Test
    void addNewStore_isValid_returnsNewStore(){
        //GIVEN
        String id = randomUUID().toString();
        StoreRecord storeRecord = new StoreRecord();
        storeRecord.setId(id);
        storeRecord.setName("storename");
        storeRecord.setAddress("storeaddress");
        storeRecord.setCity("storecity");
        storeRecord.setState("storestate");
        storeRecord.setZip("12345");
        storeRecord.setIsInRadius(true);

        Store store = new Store(storeRecord.getId(), storeRecord.getName(), storeRecord.getAddress(),
                storeRecord.getCity(), storeRecord.getState(), storeRecord.getZip(), storeRecord.isInRadius());

        //WHEN
        when(storeRepository.save(storeRecord)).thenReturn(storeRecord);
        Store newStore = storeService.addNewStore(store);

        //THEN
        assertNotNull(newStore, "The store is returned");
        assertEquals(storeRecord.getId(), newStore.getId(), "The id matches");
        assertEquals(storeRecord.getName(), newStore.getName(), "The name matches");
        assertEquals(storeRecord.getAddress(), newStore.getAddress(), "The address matches");
        assertEquals(storeRecord.getCity(), newStore.getCity(), "The city matches");
        assertEquals(storeRecord.getState(), newStore.getState(), "The state matches");
        assertEquals(storeRecord.getZip(), newStore.getZip(), "The zip matches");
        assertEquals(storeRecord.isInRadius(), newStore.isInRadius(), "The the radius matches");
    }
}