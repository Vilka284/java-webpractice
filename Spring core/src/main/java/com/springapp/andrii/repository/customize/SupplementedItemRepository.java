package com.springapp.andrii.repository.customize;

import com.springapp.andrii.model.Group;
import com.springapp.andrii.model.Item;

import java.util.List;

public interface SupplementedItemRepository {
    List<Item> getAllFiltered(Float minPrice, Float maxPrice);
    List<Item> getAllFilteredStartsWith(String nameStartWith);
    List<Item> getAllFilteredEndsWith(String nameEndsWith);
    List<Item> getAllFromGroupSortedByName(Group group, String name);
    List<Item> getAllFromGroupSortedAsc(Group group);
    List<Item> getAllFromGroupSortedDesc(Group group);
}
