package com.andrii.model;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class User {

    private int id;

    private String username;

    private String password;

    private String role;

    private boolean valid;
}
