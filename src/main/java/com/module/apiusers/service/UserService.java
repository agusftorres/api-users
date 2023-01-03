package com.module.apiusers.service;

import com.module.apiusers.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {
    User save(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
 }
