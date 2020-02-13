package com.gfi.LDVELHWebService.dal;

import org.springframework.data.repository.CrudRepository;

import com.gfi.LDVELHWebService.bo.Collection;

public interface CollectionDAO extends CrudRepository<Collection, Integer> {
 public Collection findByCollection(String collection);
}
