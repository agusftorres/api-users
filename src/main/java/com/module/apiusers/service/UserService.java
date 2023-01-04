package com.module.apiusers.service;

import com.module.apiusers.controller.model.UserRequest;
import com.module.apiusers.entity.User;
import com.module.apiusers.entity.UserPassword;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserService {
    Optional<User> findByEmail(String email);
    User signIn(UserRequest user);
    User logIn(UserPassword user);
}
