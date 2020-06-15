package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceAlreadyExistsException;
import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.Item;
import com.springapp.andrii.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements IService<Item> {

    @Autowired
    private ItemRepository itemRepository;

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
        if (!exist(item))
            itemRepository.save(item);
        else
            throw new ResourceAlreadyExistsException("Item already exist!");
    }

    @Override
    public void update(Item item, long id) {
        Item itemToUpdate = get(id);
        itemToUpdate.setName(item.getName());
        itemToUpdate.setPrice(item.getPrice());
        itemToUpdate.setGroup(item.getGroup());
        itemToUpdate.setPrice(item.getPrice());
        itemRepository.save(itemToUpdate);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public boolean exist(Item item) {
        return itemRepository.existsById(item.getId());
    }
}
