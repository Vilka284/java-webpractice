package com.andrii.dao;

import com.andrii.entity.Item;
import com.andrii.entity.Order;
import com.andrii.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDAO {

    private static OrderDAO instance;

    public static OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    public void createOrder(Order order, List<Integer> itemsIdList) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();

    }


    public void closeOrder(int orderId, int itemId, boolean buy) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        Item item = session.load(Item.class, itemId);
        if (buy) {
            item.setQuantity(item.getQuantity() - 1);
            session.update(item);
            if (item.getQuantity() <= 0)
                session.delete(item);
        }
        Order order = session.load(Order.class, itemId);
        session.delete(order);
        transaction.commit();
        session.close();
    }

    private void insertItems(int orderId, List<Integer> itemsIdList) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        String insertItemsQuery = 
                "INSERT INTO order_item VALUES (order_id, item_id) " +
                "VALUES (" + orderId + ", " + itemsIdList.get(0) + ") ";
        itemsIdList.remove(0);
        /*
        Add more items to order if it need to
         */
        for (int i:
                itemsIdList) {
            insertItemsQuery = insertItemsQuery.concat(
                    ", (" + orderId + ", " + i + ")"
            );
        }
        insertItemsQuery = insertItemsQuery.concat(";");
        Query query = session.createQuery(insertItemsQuery);
        query.executeUpdate();
    }
}
