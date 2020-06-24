package com.springapp.andrii.repository;

import com.springapp.andrii.model.User;
import com.springapp.andrii.repository.modify.ModifiedUserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, ModifiedUserRepository {
}
