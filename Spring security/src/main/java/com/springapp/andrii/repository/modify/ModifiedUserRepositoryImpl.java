package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class ModifiedUserRepositoryImpl implements ModifiedUserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<User> getByUsername(String username) {
        final String getUsername = "from User where username = " + username;
        Query query = entityManager.createQuery(getUsername, User.class);
        return (Optional<User>) query.getSingleResult();
    }
}
