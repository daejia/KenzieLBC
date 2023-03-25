package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.StoreRepository;
import com.kenzie.appserver.repositories.model.StoreRecord;
import com.kenzie.appserver.service.model.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void addNewStore() {
    }
}