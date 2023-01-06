package com.module.apiusers.service.impl;

import com.module.apiusers.controller.model.UserRequest;
import com.module.apiusers.entity.User;
import com.module.apiusers.entity.UserPassword;
import com.module.apiusers.errorhandler.ErrorCode;
import com.module.apiusers.exception.BusinessException;
import com.module.apiusers.repository.UserRepository;
import com.module.apiusers.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    private final UserRequest USER_REQUEST = UserRequest.builder()
            .email("test@example.com")
            .name("Test Name")
            .password("VGVzdDEyMw==")
            .build();
    private final User USER = User.createUser(USER_REQUEST);

    private final UserPassword REQUEST_LOGIN = UserPassword.builder()
            .email("test@example.com")
            .password("Test123")
            .build();

    @Mock
    private UserRepository userRepository;

    @Test
    public void testSignIn_UserAlreadyExists_ShouldThrowException() {
        userRepository = mock(UserRepository.class);
        UserService service = new UserServiceImpl();
        ReflectionTestUtils.setField(service, "userRepository", userRepository);

        when(userRepository.findByEmail(USER_REQUEST.getEmail())).thenReturn(Optional.of(USER));

        try {
            service.signIn(USER_REQUEST);
            fail("Expected a BusinessException to be thrown");
        } catch (BusinessException ex) {
            assertEquals(ErrorCode.INVALID_EMAIL, ex.getCode());
        }
    }

    @Test
    public void testSignIn_UserDoesNotExist_ShouldCreateNewUser() {
        userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail(USER_REQUEST.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(USER);

        UserService service = new UserServiceImpl();
        ReflectionTestUtils.setField(service, "userRepository", userRepository);
        User userCreated = service.signIn(USER_REQUEST);

        assertEquals(USER.getEmail(), userCreated.getEmail());
        assertEquals(USER.getName(), userCreated.getName());

        assertNotNull(userCreated);
    }

    @Test
    public void testLogIn_UserDoesNotExist_ShouldThrowException() {
        userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail(REQUEST_LOGIN.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(USER);

        UserService service = new UserServiceImpl();
        ReflectionTestUtils.setField(service, "userRepository", userRepository);

        try {
            service.logIn(REQUEST_LOGIN);
            fail("Expected a BusinessException to be thrown");
        } catch (BusinessException ex) {
            assertEquals(ErrorCode.WRONG_LOGIN_DATA, ex.getCode());
        }
    }

    @Test
    public void testLogIn_UserExists_ShouldUpdateUser() {
        userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail(REQUEST_LOGIN.getEmail())).thenReturn(Optional.of(USER));
        when(userRepository.save(any(User.class))).thenReturn(USER);

        UserService service = new UserServiceImpl();
        ReflectionTestUtils.setField(service, "userRepository", userRepository);

        User user = service.logIn(REQUEST_LOGIN);

        verify(userRepository).save(any(User.class));
        assertNotNull(user);
    }
}