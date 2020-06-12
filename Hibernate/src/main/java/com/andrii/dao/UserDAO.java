package com.andrii.dao;

import com.andrii.entity.Item;
import com.andrii.entity.Role;
import com.andrii.entity.User;
import com.andrii.util.HibernateUtil;
import com.github.fluent.hibernate.transformer.FluentHibernateResultTransformer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {

    private static UserDAO instance;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public boolean login(User user) {
        List<User> usersList = getUsers();
        for (User us:
             usersList) {
            if (us.equals(user))
                return true;
        }
        return false;
    }

    public boolean register(User user) {
        if (getUsers().contains(user))
            return false;
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return true;
    }

    public void setRole(int userId, int roleId) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        User user = session.load(User.class, userId);
        Role role = session.load(Role.class, roleId);
        user.setRoleByRoleId(role);
        session.update(user);
        transaction.commit();
        session.close();
    }

    private List<User> getUsers() {
        List<User> usersList;
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("from User");
        usersList = query
                .setResultTransformer(new FluentHibernateResultTransformer(Item.class))
                .list();
        session.close();
        return usersList;
    }
}
