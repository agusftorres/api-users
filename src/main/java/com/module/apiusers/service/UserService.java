package com.module.apiusers.service;

import com.module.apiusers.controller.model.UserRequest;
import com.module.apiusers.entity.User;
import com.module.apiusers.entity.UserPassword;
import org.springframework.stereotype.Component;


@Component
public interface UserService {
    User signIn(UserRequest user);
    User logIn(UserPassword user);
}
