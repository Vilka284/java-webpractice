package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.Group;
import com.springapp.andrii.model.Item;
import com.springapp.andrii.repository.GroupRepository;
import com.springapp.andrii.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService implements IService<Item>, IGroup {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Item get(long id) {
        return itemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exists"));
    }

    @Override
    public List<Item> getAll() {
        return (List<Item>) itemRepository.findAll();
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void update(Item item, long id) {
        Item itemToUpdate = get(id);
        itemToUpdate.setName(item.getName());
        itemToUpdate.setPrice(item.getPrice());
        itemToUpdate.setGroup(item.getGroup());
        itemToUpdate.setPrice(item.getPrice());
        save(itemToUpdate);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public boolean exist(Item item) {
        return itemRepository.existsById(item.getId());
    }

    @Override
    public List<Group> getFullGroupPath(Item item) {
        List<Group> groupList = (List<Group>) groupRepository.findAll();
        return groupList
                .stream()
                .filter(group -> group.equals(item.getGroup()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getItemsByGroup(Group group) {
        List<Item> itemList = getAll();
        return itemList
                .stream()
                .filter(item -> group.equals(item.getGroup()))
                .collect(Collectors.toList());
    }
}
