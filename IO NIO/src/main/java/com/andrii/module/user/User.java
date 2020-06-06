package com.andrii.module.user;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class User implements Serializable {

    private String uid, name, password, gender, preferences;

    public User(String name, String password, String gender, String preferences) {
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.preferences = preferences;
    }


}
