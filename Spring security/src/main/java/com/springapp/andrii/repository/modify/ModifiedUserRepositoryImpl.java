package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ModifiedUserRepositoryImpl implements ModifiedUserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User getByUsername(String username) {
        final String getUsername = "from User where user_name = \'" + username + "\' ";
        Query query = entityManager.createQuery(getUsername, User.class);
        return (User) query.getSingleResult();
    }
}
