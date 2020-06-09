package com.andrii.dao;

import com.andrii.entity.GroupEntity;
import com.andrii.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GroupDAO {

    public static GroupEntity getGroupById(int groupId) {
        Session session = HibernateUtil.currentSession();
        GroupEntity g = (GroupEntity) session.load(GroupEntity.class, groupId);
        session.close();
        return g;
    }

    public static void createNewGroup(GroupEntity g) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(g);
        transaction.commit();
        session.close();
    }
}
