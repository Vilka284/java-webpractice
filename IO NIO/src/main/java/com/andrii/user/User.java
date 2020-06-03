package com.andrii.user;


import java.io.Serializable;

public class User implements Serializable {

    private static int userId = 0;
    private int uid;

    private String name, password, gender, preferences;

    public User(){}

    public User(String name, String password, String gender, String preferences) {
        uid = ++userId;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.preferences = preferences;
    }

    public String getId(){
        return Integer.toString(uid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

}
