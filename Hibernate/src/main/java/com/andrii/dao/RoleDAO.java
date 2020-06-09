package com.andrii.dao;

import com.andrii.entity.RoleEntity;
import com.andrii.util.HibernateUtil;
import org.hibernate.Session;

public class RoleDAO {

    public static RoleEntity getRoleById (int roleId) {
        Session session = HibernateUtil.currentSession();
        RoleEntity r = (RoleEntity) session.load(RoleEntity.class, roleId);
        session.close();
        return r;
    }

    public static RoleEntity getRoleUser () {
        return getRoleById(1);
    }

    public static RoleEntity getRoleManager() {
        return getRoleById(2);
    }

    public static RoleEntity getRoleAdmin() {
        return getRoleById(3);
    }
}
