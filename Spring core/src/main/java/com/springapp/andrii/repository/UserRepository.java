package com.springapp.andrii.repository;

import com.springapp.andrii.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
