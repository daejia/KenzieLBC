package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ItemRepository;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.repositories.model.StoreRecord;
import com.kenzie.appserver.service.model.BrandType;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.appserver.service.model.Item;
import com.kenzie.appserver.service.model.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {
    private ItemService itemService;
    private ItemRepository itemRepository;
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
        storeService = mock(StoreService.class);
    }

    @Test
    void findById_isValid_returnsItem() {
        //GIVEN
        String storeId = randomUUID().toString();
        StoreRecord storeRecord = new StoreRecord();
        storeRecord.setId(storeId);
        storeRecord.setName("storename");
        storeRecord.setAddress("storeaddress");
        storeRecord.setCity("storecity");
        storeRecord.setState("storestate");
        storeRecord.setZip("12345");
        storeRecord.setIsInRadius(true);
        Store store = storeService.findById(storeId);

        String itemId = randomUUID().toString();
        ItemRecord itemRecord = new ItemRecord();
        itemRecord.setId(itemId);
        itemRecord.setStore(store);
        itemRecord.setName("itemname");
        itemRecord.setPrice(1.00);
        itemRecord.setCategory(Category.BEVERAGES);
        itemRecord.setBrandType(BrandType.GENERIC);
        itemRecord.setInStock(true);

        //WHEN
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(itemRecord));
        Item item = itemService.findById(itemId);

        //THEN
        assertNotNull(item, "The item is returned");
        assertEquals(itemRecord.getId(), item.getId(), "The id matches");
        assertEquals(itemRecord.getStore(), item.getStore(), "The store matches");
        assertEquals(itemRecord.getName(), item.getName(), "The name matches");
        assertEquals(itemRecord.getPrice(), item.getPrice(), "The price matches");
        assertEquals(itemRecord.getCategory(), item.getCategory(), "The category matches");
        assertEquals(itemRecord.getBrandType(), item.getBrandType(), "The brand type matches");
        assertEquals(itemRecord.isInStock(), item.getIsInStock(), "The in stock matches");
    }
    @Test
    void findById_isInvalid_returnsNull() {
        //GIVEN
        String itemId = randomUUID().toString();

        //WHEN
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());
        Item item = itemService.findById(itemId);

        //THEN
        assertNull(item, "The item is null");
    }
    @Test
    void addNewItem_isValid_returnsItem() {
        //GIVEN
        String storeId = randomUUID().toString();
        StoreRecord storeRecord = new StoreRecord();
        storeRecord.setId(storeId);
        storeRecord.setName("storename");
        storeRecord.setAddress("storeaddress");
        storeRecord.setCity("storecity");
        storeRecord.setState("storestate");
        storeRecord.setZip("12345");
        storeRecord.setIsInRadius(true);
        Store store = storeService.findById(storeId);

        String itemId = randomUUID().toString();
        ItemRecord itemRecord = new ItemRecord();
        itemRecord.setId(itemId);
        itemRecord.setStore(store);
        itemRecord.setName("itemname");
        itemRecord.setPrice(1.00);
        itemRecord.setCategory(Category.BEVERAGES);
        itemRecord.setBrandType(BrandType.GENERIC);
        itemRecord.setInStock(true);
        itemRepository.save(itemRecord);

        Item item = new Item(itemRecord.getId(), itemRecord.getStore(), itemRecord.getBrandType(), itemRecord.getName(),
                itemRecord.getCategory(), itemRecord.getPrice(), itemRecord.isInStock());

        //WHEN
        when(itemRepository.save(itemRecord)).thenReturn(itemRecord);
        Item item1 = itemService.addNewItem(item);

        //THEN
        assertNotNull(item1, "The item is returned");
        assertEquals(itemRecord.getId(), item1.getId(), "The id matches");
        assertEquals(itemRecord.getStore(), item1.getStore(), "The store matches");
        assertEquals(itemRecord.getName(), item1.getName(), "The name matches");
        assertEquals(itemRecord.getPrice(), item1.getPrice(), "The price matches");
        assertEquals(itemRecord.getCategory(), item1.getCategory(), "The category matches");
        assertEquals(itemRecord.getBrandType(), item1.getBrandType(), "The brand type matches");
        assertEquals(itemRecord.isInStock(), item1.getIsInStock(), "The in stock matches");
    }

    @Test
    void findAllItems_isValid_returnsAllItems() {
        //GIVEN
        String storeId = randomUUID().toString();
        StoreRecord storeRecord = new StoreRecord();
        storeRecord.setId(storeId);
        storeRecord.setName("storename");
        storeRecord.setAddress("storeaddress");
        storeRecord.setCity("storecity");
        storeRecord.setState("storestate");
        storeRecord.setZip("12345");
        storeRecord.setIsInRadius(true);
        Store store = storeService.findById(storeId);

        String itemId = randomUUID().toString();
        String itemId1 = randomUUID().toString();
        ItemRecord itemRecord = new ItemRecord();
        itemRecord.setId(itemId);
        itemRecord.setStore(store);
        itemRecord.setName("itemname");
        itemRecord.setPrice(1.00);
        itemRecord.setCategory(Category.BEVERAGES);
        itemRecord.setBrandType(BrandType.GENERIC);
        itemRecord.setInStock(true);
        itemRepository.save(itemRecord);

        ItemRecord itemRecord1 = new ItemRecord();
        itemRecord1.setId(itemId1);
        itemRecord1.setStore(store);
        itemRecord1.setName("itemname1");
        itemRecord1.setPrice(1.00);
        itemRecord1.setCategory(Category.PASTA);
        itemRecord1.setBrandType(BrandType.NAME_BRAND);
        itemRecord1.setInStock(true);
        itemRepository.save(itemRecord1);

        List<ItemRecord> itemRecords = new ArrayList<>();
        itemRecords.add(itemRecord);
        itemRecords.add(itemRecord1);

        //WHEN
        when(itemRepository.findAll()).thenReturn(itemRecords);
        List<Item> items = itemService.findAllItems();

        //THEN
        assertNotNull(itemRecords, "The items are returned");
        assertEquals(itemRecords.size(), items.size(), "The size matches");
    }

    @Test
    void findAllItems_isInvalid_returnsNull() {
        //GIVEN

        //WHEN
        when(itemRepository.findAll()).thenReturn(null);
        Iterable<ItemRecord> itemRecords = itemRepository.findAll();

        //THEN
        assertNull(itemRecords, "The items are null");
    }

    @Test
    void updateItem_isValid_isSuccessful(){
        //GIVEN
        String storeId = randomUUID().toString();
        StoreRecord storeRecord = new StoreRecord();
        storeRecord.setId(storeId);
        storeRecord.setName("storename");
        storeRecord.setAddress("storeaddress");
        storeRecord.setCity("storecity");
        storeRecord.setState("storestate");
        storeRecord.setZip("12345");
        storeRecord.setIsInRadius(true);
        Store store = storeService.findById(storeId);

        String itemId = randomUUID().toString();
        ItemRecord itemRecord = new ItemRecord();
        itemRecord.setId(itemId);
        itemRecord.setStore(store);
        itemRecord.setName("itemname");
        itemRecord.setPrice(1.00);
        itemRecord.setCategory(Category.BEVERAGES);
        itemRecord.setBrandType(BrandType.GENERIC);
        itemRecord.setInStock(true);
        itemRepository.save(itemRecord);

        Item item = new Item(itemRecord.getId(), itemRecord.getStore(), itemRecord.getBrandType(), itemRecord.getName(),
                itemRecord.getCategory(), itemRecord.getPrice(), itemRecord.isInStock());

        //WHEN
        when(itemRepository.save(itemRecord)).thenReturn(itemRecord);
        Item updatedItem = itemService.updateItem(item);

        //THEN
        assertNotNull(updatedItem, "The item is returned");
        assertEquals(itemRecord.getId(), updatedItem.getId(), "The id matches");
        assertEquals(itemRecord.getStore(), updatedItem.getStore(), "The store matches");
        assertEquals(itemRecord.getName(), updatedItem.getName(), "The name matches");
        assertEquals(itemRecord.getPrice(), updatedItem.getPrice(), "The price matches");
        assertEquals(itemRecord.getCategory(), updatedItem.getCategory(), "The category matches");
        assertEquals(itemRecord.getBrandType(), updatedItem.getBrandType(), "The brand type matches");
        assertEquals(itemRecord.isInStock(), updatedItem.getIsInStock(), "The in stock matches");
    }
}