package com.springapp.andrii.repository;

import com.springapp.andrii.model.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
