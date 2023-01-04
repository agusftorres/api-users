package com.module.apiusers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.module.apiusers.controller.model.UserRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    UUID uuid;
    String name;
    String email;
    @JsonIgnore
    String password;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "users_phone",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    List<Phone> phones;

    String created;
    String modified;
    String lastLogin;
    String token;
    boolean isActive;

    public static User createUser(UserRequest request) {
        return User.builder()
                .uuid(UUID.randomUUID())
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .created(LocalDateTime.now().toString())
                .modified(LocalDateTime.now().toString())
                .lastLogin(LocalDateTime.now().toString())
                .token("sadasdsa")
                .isActive(true)
                .phones(request.getPhones()
                        .stream()
                        .map(Phone::fromModel)
                        .collect(Collectors.toList()))
                .build();
    }

}
