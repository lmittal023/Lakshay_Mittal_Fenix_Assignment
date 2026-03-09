package com.fenix.platform.controller;

import com.fenix.platform.entity.User;
import com.fenix.platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

@PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

@GetMapping
    public Page<User> getAllUsers(
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

@GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

@PutMapping("/{id}")
    public User updateUser(
            @PathVariable("id") Long id,
            @RequestBody User user) {
        return userService.updateUser(id, user);
    }

@DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
