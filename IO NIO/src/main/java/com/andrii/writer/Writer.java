package com.andrii.writer;

import java.util.HashSet;

public interface Writer {

    HashSet<?> readUsers();
    boolean writeUsers(String[] newUser);
    boolean deleteUsers(String deleteParam);
    boolean updateUsers(String data, String[] newUser);
}
