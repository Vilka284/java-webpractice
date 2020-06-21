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

    @GetMapping("/items/filter")
    public List<Item> getFilteredItems(@RequestParam(defaultValue = "0.0", required = false) float minPrice,
                                 @RequestParam(defaultValue = "0.0", required = false) float maxPrice,
                                 @RequestParam(required = false) String startsWith,
                                 @RequestParam(required = false) String endsWith) {
        List<Item> itemList = itemService.getAll();
        if (minPrice >= 0 && maxPrice > 0) filterByPrice(itemList, minPrice, maxPrice);
        if (startsWith != null) filterByNameStart(itemList, startsWith);
        if (endsWith != null) filterByNameEnd(itemList, endsWith);
        return itemList;
    }

    @PostMapping("/items")
    public ResponseEntity<?> createItem(@Valid @RequestBody Item itemRequest) {
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

    private void filterByPrice(List<Item> itemList, float minPrice, float maxPrice) {
        final List<Item> filteredItemList = itemService.getFilteredByPrice(minPrice, maxPrice);
        itemList.removeIf(item -> !filteredItemList.contains(item));
    }

    private void filterByNameStart(List<Item> itemList, String startsWith) {
        final List<Item> filteredItemList = itemService.getFilteredByNameStartsWith(startsWith);
        itemList.removeIf(item -> !filteredItemList.contains(item));
    }

    private void filterByNameEnd(List<Item> itemList, String endsWith) {
        final List<Item> filteredItemList = itemService.getFilteredByNameEndsWith(endsWith);
        itemList.removeIf(item -> !filteredItemList.contains(item));
    }
}
