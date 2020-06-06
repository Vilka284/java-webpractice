package com.andrii.dao;

import com.andrii.module.user.User;

import java.util.HashSet;

public interface Cruder {

    HashSet<User> readUsers();
    boolean writeUser(User u);
    boolean deleteUser(User u);
    boolean updateUser(User u, User newU);
}
