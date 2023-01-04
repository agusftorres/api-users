package com.module.apiusers.controller.model;

import com.module.apiusers.entity.Phone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Getter
public class UserRequest {
    public static final String EMAIL_VALIDATOR = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    public static final String PASSWORD_VALIDATOR = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";

    @NotBlank
    String name;

    @NotBlank
    @Pattern(regexp = EMAIL_VALIDATOR)
    String email;

    @NotBlank
    @Pattern(regexp = PASSWORD_VALIDATOR)
    String password;

    List<PhoneModel> phones;
}
