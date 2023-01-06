package com.module.apiusers.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPassword {
    String email;
    String password;
}
