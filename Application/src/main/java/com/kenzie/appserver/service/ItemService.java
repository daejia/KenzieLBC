package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ItemRepository;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findById(String id) {
        Item itemFromBackend = itemRepository
                .findById(id)
                .map(item -> new Item(item.getId(), item.getStore(), item.getBrandType(), item.getName(),item.getCategory(),
                        item.getPrice(), item.isInStock()))
                .orElse(null);

        return itemFromBackend;
    }
    public Item addNewItem(Item item) {
        ItemRecord itemRecord = new ItemRecord();
        itemRecord.setId(item.getId());
        itemRecord.setStore(item.getStore());
        itemRecord.setBrandType(item.getBrandType());
        itemRecord.setName(item.getName());
        itemRecord.setCategory(item.getCategory());
        itemRecord.setPrice(item.getPrice());
        itemRecord.setInStock(item.getIsInStock());
        itemRepository.save(itemRecord);
        return item;
    }
}
