package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.Group;
import com.springapp.andrii.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GroupService implements IService<Group> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group get(long id) {
        return groupRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group with id not exist"));
    }

    @Override
    public List<Group> getAll() {
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public void save(Group group) {
        groupRepository.save(group);
    }

    @Override
    public void update(Group group, long id) {
        Group groupToUpdate = get(id);
        groupToUpdate.setName(group.getName());
        groupToUpdate.setParentGroup(group.getParentGroup());
        save(groupToUpdate);
    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }

    @Override
    public boolean exist(Group group) {
        return groupRepository.existsById(group.getId());
    }
}
