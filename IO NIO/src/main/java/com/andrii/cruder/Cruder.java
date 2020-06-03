package com.andrii.cruder;

import com.andrii.user.User;

import java.util.HashSet;

public interface Cruder {

    HashSet<User> readUsers();
    boolean writeUsers(User u);
    boolean deleteUsers(User u);
    boolean updateUsers(User u, User newU);
}
