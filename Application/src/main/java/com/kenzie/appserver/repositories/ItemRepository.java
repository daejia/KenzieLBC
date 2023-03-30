package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface ItemRepository extends CrudRepository<ItemRecord, String> {
    Optional<Object> findById(Long cartId);

    List<Item> findByCartId(Long cartId);
}
