package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.Group;
import com.springapp.andrii.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ModifiedItemRepositoryImpl implements ModifiedItemRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Item> getAllFilteredByPrice(Float minPrice, Float maxPrice) {
        final String getItemsFilteredByPrice = "SELECT * " +
                "FROM item WHERE price > " + minPrice + " AND price < " + maxPrice + ";";
        Query query = entityManager.createQuery(getItemsFilteredByPrice, Item.class);
        return (List<Item>) query.getResultList();
    }

    @Override
    public List<Item> getAllFilteredStartsWith(String nameStartsWith) {
        final String getItemsFilteredByName = "SELECT * " +
                "FROM item WHERE name LIKE \'" + nameStartsWith + "%\' ;";
        Query query = entityManager.createQuery(getItemsFilteredByName, Item.class);
        return (List<Item>) query.getResultList();
    }

    @Override
    public List<Item> getAllFilteredEndsWith(String nameEndsWith) {
        final String getItemsFilteredByName = "SELECT * " +
                "FROM item WHERE name LIKE \'%" + nameEndsWith + "\' ;";
        Query query = entityManager.createQuery(getItemsFilteredByName, Item.class);
        return (List<Item>) query.getResultList();
    }

    @Override
    public List<Item> getAllFromGroupSortedByNameAsc(Group group) {
        final String getItemsSortedByName = "SELECT * " +
                " FROM item " +
                " WHERE \'group\' = " + group.getId() +
                " ORDER BY price, name;";
        Query query = entityManager.createQuery(getItemsSortedByName, Item.class);
        return (List<Item>) query.getResultList();
    }

    @Override
    public List<Item> getAllFromGroupSortedByNameDesc(Group group) {
        final String getItemsSortedByName = "SELECT * " +
                " FROM item " +
                " WHERE \'group\' = " + group.getId() +
                " ORDER BY price DESC, name;";
        Query query = entityManager.createQuery(getItemsSortedByName, Item.class);
        return (List<Item>) query.getResultList();
    }
}
