package com.andrii.dao;

import com.andrii.entity.Group;
import com.andrii.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GroupDAO {

    private static GroupDAO instance;

    public static GroupDAO getInstance() {
        if (instance == null) {
            instance = new GroupDAO();
        }
        return instance;
    }

    public Group getGroupById(int groupId) {
        Session session = HibernateUtil.currentSession();
        Group group = (Group) session.load(Group.class, groupId);
        session.close();
        return group;
    }

    public void createNewGroup(Group group) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(group);
        transaction.commit();
        session.close();
    }
}
