package com.healthhalo.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    private String fullname;
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    private Role role;
}
