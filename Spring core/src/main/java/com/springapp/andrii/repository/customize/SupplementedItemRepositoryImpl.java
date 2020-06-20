package com.springapp.andrii.repository.customize;

import com.springapp.andrii.model.Group;
import com.springapp.andrii.model.Item;

import java.util.List;

public class SupplementedItemRepositoryImpl implements SupplementedItemRepository {

    @Override
    public List<Item> getAllFiltered(Float minPrice, Float maxPrice) {
        return null;
    }

    @Override
    public List<Item> getAllFilteredStartsWith(String nameStartWith) {
        return null;
    }

    @Override
    public List<Item> getAllFilteredEndsWith(String nameEndsWith) {
        return null;
    }

    @Override
    public List<Item> getAllFromGroupSortedByName(Group group, String name) {
        return null;
    }

    @Override
    public List<Item> getAllFromGroupSortedAsc(Group group) {
        return null;
    }

    @Override
    public List<Item> getAllFromGroupSortedDesc(Group group) {
        return null;
    }
}
