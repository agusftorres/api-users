package com.module.apiusers.service.impl;

import com.module.apiusers.entity.UserPassword;
import com.module.apiusers.errorhandler.ErrorCode;
import com.module.apiusers.controller.model.UserRequest;
import com.module.apiusers.entity.User;
import com.module.apiusers.exception.BusinessException;
import com.module.apiusers.repository.UserRepository;
import com.module.apiusers.security.TokenUtils;
import com.module.apiusers.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User signIn(UserRequest request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);


        final Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            throw new BusinessException(ErrorCode.INVALID_EMAIL);
        }
        return userRepository.save(User.createUser(request));
    }

    @Override
    public User logIn(UserPassword data) {
        final Optional<User> optional = userRepository.findByEmail(data.getEmail());
        if(optional.isEmpty()){
            throw new BusinessException(ErrorCode.WRONG_LOGIN_DATA);
        }
        return updateUser(optional);
    }

    private User updateUser(Optional<User> optional) {
        final User user = optional.get();
        user.setLastLogin(LocalDateTime.now().toString());
        user.setToken(TokenUtils.createToken(user.getName(), user.getEmail()));
        userRepository.save(user);
        return user;
    }

}
