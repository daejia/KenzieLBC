package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.CartRecord;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<CartRecord, String> {
}
