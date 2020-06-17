package com.springapp.andrii.service;

import com.springapp.andrii.model.Group;
import com.springapp.andrii.model.Item;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface IGroup {

    List<Group> getFullGroupPath(Item item);

    List<Item> getItemsByGroup(Group group);
}
