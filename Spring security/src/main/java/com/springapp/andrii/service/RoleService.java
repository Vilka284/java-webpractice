package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.Role;
import com.springapp.andrii.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role get(long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not exist"));
    }
}
