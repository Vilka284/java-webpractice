package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.User;

public interface ModifiedUserRepository {
    User getByUsername(String username);
}
