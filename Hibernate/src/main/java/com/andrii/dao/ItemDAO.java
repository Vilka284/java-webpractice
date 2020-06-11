package com.andrii.dao;


import com.andrii.entity.Group;
import com.andrii.entity.Item;
import com.andrii.util.HibernateUtil;
import com.github.fluent.hibernate.transformer.FluentHibernateResultTransformer;
import lombok.Singleton;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Singleton(style = Singleton.Style.HOLDER)
public class ItemDAO {

    public Item getItemById(int itemId){
        Session session = HibernateUtil.currentSession();
        Item item = session.load(Item.class, itemId);
        session.close();
        return item;
    }

    public List<Item> getItemsByGroupId(int groupId) {
        List<Item> itemsList;
        Session session = HibernateUtil.currentSession();
        Group group = session.load(Group.class, groupId);
        Query query = session.createQuery("from Item where Item.groupByGroupId =" + group);
        itemsList = query
                .setResultTransformer(new FluentHibernateResultTransformer(Item.class))
                .list();
        session.close();
        return itemsList;
    }

    public void insertItem(Item item){
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        session.close();
    }

    public void removeItem(int itemId){
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(Item.class, itemId));
        transaction.commit();
        session.close();
    }
}
