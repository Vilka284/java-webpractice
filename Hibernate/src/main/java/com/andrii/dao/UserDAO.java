package com.andrii.dao;

import com.andrii.entity.RoleEntity;
import com.andrii.entity.UserEntity;
import com.andrii.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static boolean login(UserEntity u) {
        List<UserEntity> usersList = getUsers();
        for (UserEntity us:
             usersList) {
            if (us.equals(u))
                return true;
        }
        return false;
    }

    public static boolean register(UserEntity u) {
        if (getUsers().contains(u))
            return false;
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(u);
        transaction.commit();
        session.close();
        return true;
    }

    public static void setRole(int userId, int roleId) {
        Session session = HibernateUtil.currentSession();
        Transaction transaction = session.beginTransaction();
        UserEntity u = session.load(UserEntity.class, userId);
        RoleEntity r = session.load(RoleEntity.class, roleId);
        u.setRoleByRoleId(r);
        session.update(u);
        transaction.commit();
        session.close();
    }

    private static List<UserEntity> getUsers() {
        List<UserEntity> usersList;
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("from UserEntity");
        usersList = query.list();
        session.close();
        return usersList;
    }
}
