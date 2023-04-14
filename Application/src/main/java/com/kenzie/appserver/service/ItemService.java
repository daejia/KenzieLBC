package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ItemRepository;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private static ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findById(String id) {
        Item itemFromBackend = itemRepository
                .findById(id)
                .map(item -> new Item(item.getId(), item.getStore(), item.getBrandType(), item.getName(), item.getCategory(),
                        item.getPrice(), item.isInStock()))
                .orElse(null);

        return itemFromBackend;
    }

    public Item addNewItem(Item item) {
        createItemRecord(item);
        return item;
    }

    public Item updateItem(Item item) {
        createItemRecord(item);
        return item;
    }

    public static List<Item> findAllItems() {
        List<Item> items = new ArrayList<>();

        Iterable<ItemRecord> itemIterator = itemRepository.findAll();
        for(ItemRecord record : itemIterator) {
            items.add(new Item(record.getId(), record.getStore(), record.getBrandType(), record.getName(),
                    record.getCategory(), record.getPrice(), record.isInStock()));
        }
        return items;
    }
    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }

    private void createItemRecord(Item item) {
        ItemRecord itemRecord = new ItemRecord();
        itemRecord.setId(item.getId());
        itemRecord.setStore(item.getStore());
        itemRecord.setBrandType(item.getBrandType());
        itemRecord.setName(item.getName());
        itemRecord.setCategory(item.getCategory());
        itemRecord.setPrice(item.getPrice());
        itemRecord.setInStock(item.getIsInStock());
        itemRepository.save(itemRecord);
    }
}

