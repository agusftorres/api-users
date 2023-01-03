package com.module.apiusers.controller;

import com.module.apiusers.entity.User;
import com.module.apiusers.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @GetMapping("/{email}")
    public User getUserById(@PathVariable String email){
        Optional<User> user = userService.findByEmail(email);
        return user.get();
    }
}
