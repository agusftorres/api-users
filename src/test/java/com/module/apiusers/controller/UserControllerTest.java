package com.module.apiusers.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.module.apiusers.controller.model.UserRequest;
import com.module.apiusers.entity.User;
import com.module.apiusers.entity.UserPassword;
import com.module.apiusers.service.UserService;
import com.module.apiusers.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

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

    @Test
    public void testSignIn_ValidRequest_ShouldReturnHttpStatusCreated() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        userService = mock(UserServiceImpl.class);
        when(userService.signIn(USER_REQUEST)).thenReturn(USER);

        ReflectionTestUtils.setField(userController, "userService", userService);
        final ResponseEntity<User> userResponseEntity = userController.signIn(USER_REQUEST);
        assertThat(userResponseEntity.getStatusCode().value()).isEqualTo(201);
        assertThat(userResponseEntity.getBody().getCreated()).isNotNull();
    }

    @Test
    public void testLogIn_ValidRequest_ShouldReturnHttpStatusCreated() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        userService = mock(UserServiceImpl.class);
        when(userService.logIn(REQUEST_LOGIN)).thenReturn(USER);

        ReflectionTestUtils.setField(userController, "userService", userService);
        final ResponseEntity<User> userResponseEntity = userController.logIn(REQUEST_LOGIN);
        assertThat(userResponseEntity.getStatusCode().value()).isEqualTo(202);
        assertThat(userResponseEntity.getBody().getCreated()).isNotNull();
    }

}