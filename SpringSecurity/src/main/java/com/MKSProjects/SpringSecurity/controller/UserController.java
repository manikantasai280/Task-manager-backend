package com.MKSProjects.SpringSecurity.controller;

import com.MKSProjects.SpringSecurity.entity.User;
import com.MKSProjects.SpringSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public User createUser(@RequestBody User user){

        return userService.createUser(user);
    }
    @GetMapping("/{id}")
    public Optional<User>  findById(@PathVariable Long id){
        return userService.findById(id);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){

        userService.deleteById(id);
    }
}
