package com.springapp.andrii.controller.userController;

import com.springapp.andrii.exception.ResourceAlreadyExistsException;
import com.springapp.andrii.model.User;
import com.springapp.andrii.service.RoleService;
import com.springapp.andrii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/{userId}/changerole/{roleId}")
    public ResponseEntity<?> changeUserRole(@PathVariable Long userId,
                                            @PathVariable Long roleId) {
        User user = userService.get(userId);
        user.setRole(roleService.get(roleId));
        userService.update(user, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/add")
    public ResponseEntity<?> createUser(@Valid @RequestBody User userRequest) {
        if (userService.exist(userRequest))
            throw new ResourceAlreadyExistsException("This item already exist!");
        userService.save(userRequest);
        return userService.exist(userRequest)
                ? ResponseEntity.ok().build()
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/user/all")
    public List<User> getUserById() {
        return userService.getAll();
    }
}
