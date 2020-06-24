package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.User;

import java.util.Optional;

public interface ModifiedUserRepository {
    Optional<User> getByUsername(String username);
}
