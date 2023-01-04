package com.module.apiusers.controller;

import com.module.apiusers.controller.model.UserRequest;
import com.module.apiusers.entity.User;
import com.module.apiusers.entity.UserPassword;
import com.module.apiusers.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public User getUserById(@PathVariable String email){
        Optional<User> user = userService.findByEmail(email);
        return user.get();
    }

    @PostMapping("/signin")
    public ResponseEntity<User> signIn(@Valid @RequestBody UserRequest request){
        final User user = userService.signIn(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/login")
    public ResponseEntity<User> logIn(@Valid @RequestBody UserPassword userPassword){
        final User user = userService.logIn(userPassword);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }
}
