package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.Order;
import com.springapp.andrii.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ModifiedOrderRepositoryImpl implements ModifiedOrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Order> getUserOrders(User user) {
        final String getUserOrders = "from Order where user_id = " + user.getId();
        Query query = entityManager.createQuery(getUserOrders, Order.class);
        return (List<Order>) query.getResultList();
    }

    @Override
    public List<Order> getClosedUserOrders(User user) {
        final String getUserOrders = "from Order where user_id = " + user.getId() + " and status like \'closed\'";
        Query query = entityManager.createQuery(getUserOrders, Order.class);
        return (List<Order>) query.getResultList();
    }
}
