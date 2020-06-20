package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.Group;
import com.springapp.andrii.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group get(long id) {
        return groupRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group with id not exist"));
    }

    public List<Group> getAll() {
        return (List<Group>) groupRepository.findAll();
    }

    public void save(Group group) {
        groupRepository.save(group);
    }

    public void update(Group group, long id) {
        Group groupToUpdate = get(id);
        groupToUpdate.setName(group.getName());
        groupToUpdate.setParentGroup(group.getParentGroup());
        save(groupToUpdate);
    }

    public void delete(Group group) {
        groupRepository.delete(group);
    }

    public boolean exist(Group group) {
        return groupRepository.existsById(group.getId());
    }
}
