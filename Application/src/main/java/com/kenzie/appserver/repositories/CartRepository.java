package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.CartRecord;
import com.kenzie.appserver.service.model.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<CartRecord, String> {
//    List<Cart> findByCartId(Long cartId);
}
