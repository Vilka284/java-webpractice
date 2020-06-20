package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.Group;
import com.springapp.andrii.model.Item;
import com.springapp.andrii.repository.GroupRepository;
import com.springapp.andrii.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private GroupRepository groupRepository;

    public Item get(long id) {
        return itemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exists"));
    }

    public List<Item> getAll() {
        return (List<Item>) itemRepository.findAll();
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public void update(Item item, long id) {
        Item itemToUpdate = get(id);
        itemToUpdate.setName(item.getName());
        itemToUpdate.setPrice(item.getPrice());
        itemToUpdate.setGroup(item.getGroup());
        itemToUpdate.setPrice(item.getPrice());
        save(itemToUpdate);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public boolean exist(Item item) {
        return itemRepository.existsById(item.getId());
    }

    public List<Group> getFullGroupPath(Item item) {
        List<Group> groupList = (List<Group>) groupRepository.findAll();
        return groupList
                .stream()
                .filter(group -> group.equals(item.getGroup()))
                .collect(Collectors.toList());
    }

    public List<Item> getItemsByGroup(Group group, String order) {
        if (order.equals("asc"))
            return itemRepository.getAllFromGroupSortedByNameAsc(group);
        else if (order.equals("desc"))
            return itemRepository.getAllFromGroupSortedByNameDesc(group);
        else
            throw new ResourceNotFoundException("Unknown sorting order");
    }

    public List<Item> getFilteredByPrice(float min, float max) {
        return itemRepository.getAllFilteredByPrice(min, max);
    }

    public List<Item> getFilteredByNameStartsWith(String startsWith) {
        return itemRepository.getAllFilteredStartsWith(startsWith);
    }

    public List<Item> getFilteredByNameEndsWith(String endsWith) {
        return itemRepository.getAllFilteredEndsWith(endsWith);
    }

}
