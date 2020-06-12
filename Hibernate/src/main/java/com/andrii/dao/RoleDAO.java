package com.andrii.dao;

import com.andrii.entity.Role;
import com.andrii.util.HibernateUtil;
import org.hibernate.Session;

public class RoleDAO {

    private static RoleDAO instance;

    public static RoleDAO getInstance() {
        if (instance == null) {
            instance = new RoleDAO();
        }
        return instance;
    }

    public Role getRoleById (int roleId) {
        Session session = HibernateUtil.currentSession();
        Role role = (Role) session.load(Role.class, roleId);
        session.close();
        return role;
    }

    public Role getRoleUser () {
        return getRoleById(1);
    }

    public Role getRoleManager() {
        return getRoleById(2);
    }

    public Role getRoleAdmin() {
        return getRoleById(3);
    }
}
