package com.springapp.andrii.repository;

import com.springapp.andrii.model.Item;
import com.springapp.andrii.repository.modify.ModifiedItemRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long>, ModifiedItemRepository {
}
