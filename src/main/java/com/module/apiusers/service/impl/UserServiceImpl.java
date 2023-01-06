package com.module.apiusers.service.impl;

import com.module.apiusers.entity.UserPassword;
import com.module.apiusers.errorhandler.ErrorCode;
import com.module.apiusers.controller.model.UserRequest;
import com.module.apiusers.entity.User;
import com.module.apiusers.exception.BusinessException;
import com.module.apiusers.repository.UserRepository;
import com.module.apiusers.security.TokenUtils;
import com.module.apiusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();

    @Override
    public User signIn(UserRequest request) {
        final Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            throw new BusinessException(ErrorCode.INVALID_EMAIL);
        }
        request.setPassword(encoder.encodeToString(request.getPassword().getBytes()));
        return userRepository.save(User.createUser(request));
    }

    @Override
    public User logIn(UserPassword data) {
        final Optional<User> optional = userRepository.findByEmail(data.getEmail());
        if(optional.isEmpty() || !validatePassword(optional.get(), data)){
            throw new BusinessException(ErrorCode.WRONG_LOGIN_DATA);
        }
        return updateUser(optional);
    }

    private boolean validatePassword(User user, UserPassword data) {
        return data.getPassword().equals(
                new String(decoder.decode(user.getPassword()))
        );
    }

    private User updateUser(Optional<User> optional) {
        final User user = optional.get();
        user.setLastLogin(LocalDateTime.now().toString());
        user.setToken(TokenUtils.createToken(user.getName(), user.getEmail()));
        userRepository.save(user);
        return user;
    }

}
