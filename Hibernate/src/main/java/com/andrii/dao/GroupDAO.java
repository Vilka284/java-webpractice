package com.andrii.dao;

import com.andrii.entity.Group;
import com.andrii.util.HibernateUtil;
import lombok.Singleton;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Singleton(style = Singleton.Style.HOLDER)
public class GroupDAO {

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
