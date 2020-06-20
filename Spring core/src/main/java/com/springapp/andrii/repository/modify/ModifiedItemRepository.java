package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.Group;
import com.springapp.andrii.model.Item;

import java.util.List;

public interface ModifiedItemRepository {
    List<Item> getAllFilteredByPrice(Float minPrice, Float maxPrice);
    List<Item> getAllFilteredStartsWith(String nameStartsWith);
    List<Item> getAllFilteredEndsWith(String nameEndsWith);
    List<Item> getAllFromGroupSortedByNameAsc(Group group);
    List<Item> getAllFromGroupSortedByNameDesc(Group group);
}
