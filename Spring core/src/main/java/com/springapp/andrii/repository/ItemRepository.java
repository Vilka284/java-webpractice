package com.springapp.andrii.repository;

import com.springapp.andrii.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
