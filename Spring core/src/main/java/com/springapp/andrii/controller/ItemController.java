package com.springapp.andrii.controller;

import com.springapp.andrii.exception.ResourceAlreadyExistsException;
import com.springapp.andrii.model.Item;
import com.springapp.andrii.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getItems() {
        return itemService.getAll();
    }

    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.get(id);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@Valid @RequestBody Item itemRequest) {
        if (itemService.exist(itemRequest))
            throw new ResourceAlreadyExistsException("This item already exist!");
        itemService.save(itemRequest);
        return itemService.exist(itemRequest)
                ? ResponseEntity.ok().build()
                : ResponseEntity.noContent().build();
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id,
                                        @Valid @RequestBody Item itemRequest) {
        itemService.update(itemRequest, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        Item item = itemService.get(id);
        itemService.delete(item);
        return !itemService.exist(item)
                ? ResponseEntity.ok().build()
                : ResponseEntity.noContent().build();
    }
}
