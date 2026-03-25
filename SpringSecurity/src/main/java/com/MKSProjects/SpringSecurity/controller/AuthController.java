package com.MKSProjects.SpringSecurity.controller;

import com.MKSProjects.SpringSecurity.entity.Role;
import com.MKSProjects.SpringSecurity.entity.User;
import com.MKSProjects.SpringSecurity.security.JwtService;
import com.MKSProjects.SpringSecurity.service.CustomUserDetailsService;
import com.MKSProjects.SpringSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    @PostMapping("/register")
    public String register(@RequestBody User user){

        if(userService.findByUserName(user.getUsername()).isPresent()){
            return "Username already exists";
        }

        // ✅ Set default role here
        user.setRole(Role.ROLE_USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);

        return "User registered successfully";
    }
    @PostMapping("/login")
    public String Login(@RequestBody User user){
        UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUsername());
        if(!passwordEncoder.matches(user.getPassword(),userDetails.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        return jwtService.generateToken(userDetails);
    }
}
