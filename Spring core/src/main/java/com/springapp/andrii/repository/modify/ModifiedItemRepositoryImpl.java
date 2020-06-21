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
        final String getItemsFilteredByPrice = "from Item where price > " + minPrice + " and price < " + maxPrice;
        Query query = entityManager.createQuery(getItemsFilteredByPrice, Item.class);
        return (List<Item>) query.getResultList();
    }

    @Override
    public List<Item> getAllFilteredStartsWith(String nameStartsWith) {
        final String getItemsFilteredByName = "from Item where name like \'" + nameStartsWith + "%\'";
        Query query = entityManager.createQuery(getItemsFilteredByName, Item.class);
        return (List<Item>) query.getResultList();
    }

    @Override
    public List<Item> getAllFilteredEndsWith(String nameEndsWith) {
        final String getItemsFilteredByName = "from Item where name like \'%" + nameEndsWith + "\'";
        Query query = entityManager.createQuery(getItemsFilteredByName, Item.class);
        return (List<Item>) query.getResultList();
    }

    @Override
    public List<Item> getAllFromGroupSortedByNameAsc(Group group) {
        final String getItemsSortedByName = "from Item " +
                " where \'group\' = " + group.getId() +
                " order by price asc, name asc";
        Query query = entityManager.createQuery(getItemsSortedByName, Item.class);
        return (List<Item>) query.getResultList();
    }

    @Override
    public List<Item> getAllFromGroupSortedByNameDesc(Group group) {
        final String getItemsSortedByName = "from Item " +
                " where \'group\' = " + group.getId() +
                " order by price desc, name";
        Query query = entityManager.createQuery(getItemsSortedByName, Item.class);
        return (List<Item>) query.getResultList();
    }
}
