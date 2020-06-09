package com.andrii.dao;


import com.andrii.entity.GroupEntity;
import com.andrii.entity.ItemEntity;
import com.andrii.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDAO {

    public static ItemEntity getItemById(int itemId){
        Session session = HibernateUtil.currentSession();
        ItemEntity i = session.load(ItemEntity.class, itemId);
        session.close();
        return i;
    }

    public static List<ItemEntity> getItemsByGroupId(int groupId) {
        List<ItemEntity> itemsList;
        Session session = HibernateUtil.currentSession();
        GroupEntity g = session.load(GroupEntity.class, groupId);
        Query query = session.createQuery("from ItemEntity where ItemEntity.groupByGroupId =" + g);
        itemsList = query.list();
        session.close();
        return itemsList;
    }

    public static void insertItem(ItemEntity i){
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(i);
        transaction.commit();
        session.close();
    }

    public static void removeItem(int itemId){
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(ItemEntity.class, itemId));
        transaction.commit();
        session.close();
    }
}
