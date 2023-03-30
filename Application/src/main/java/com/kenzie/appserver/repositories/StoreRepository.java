package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.StoreRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface StoreRepository extends CrudRepository<StoreRecord, String> {
}
