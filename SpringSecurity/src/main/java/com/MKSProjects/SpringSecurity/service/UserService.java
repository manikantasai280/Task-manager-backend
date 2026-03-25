package com.MKSProjects.SpringSecurity.service;

import com.MKSProjects.SpringSecurity.entity.User;
import com.MKSProjects.SpringSecurity.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    public User createUser(User user){
        return userRepo.save(user);
    }
    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public Optional<User> findByUserName(String username){
        return userRepo.findByUsername(username);
    }
    public void deleteById(Long id){
        userRepo.deleteById(id);
    }
}
