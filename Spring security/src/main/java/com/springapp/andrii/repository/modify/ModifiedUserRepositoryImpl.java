package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.User;
import com.springapp.andrii.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ModifiedUserRepositoryImpl implements ModifiedUserRepository {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User getByUsername(String username) {
        final String getUsername = "from User where user_name = \'" + username + "\' ";
        Query query = entityManager.createQuery(getUsername, User.class);
        log.info(query.getSingleResult().toString());
        return (User) query.getSingleResult();
    }
}
