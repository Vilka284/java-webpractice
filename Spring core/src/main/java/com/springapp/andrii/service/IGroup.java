package com.springapp.andrii.service;

import com.springapp.andrii.model.Group;
import com.springapp.andrii.model.Item;

import javax.transaction.Transactional;
import java.util.List;

public interface IGroup {

    @Transactional
    List<Group> getFullGroupPath(Item item);

    @Transactional
    List<Item> getItemsByGroup(Group group);
}
