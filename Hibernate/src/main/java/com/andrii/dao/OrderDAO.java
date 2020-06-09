package com.andrii.dao;

import com.andrii.entity.ItemEntity;
import com.andrii.entity.OrderEntity;
import com.andrii.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class OrderDAO {

    public static void createOrder(OrderEntity o) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(o);
        transaction.commit();
        session.close();
    }


    public static void closeOrder(int itemId, boolean buy) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        ItemEntity i = session.load(ItemEntity.class, itemId);
        if (buy) {
            i.setQuantity(i.getQuantity() - 1);
            session.update(i);
            if (i.getQuantity() <= 0)
                session.delete(i);
        }
        OrderEntity o = session.load(OrderEntity.class, itemId);
        session.delete(o);
        transaction.commit();
        session.close();
    }
}
