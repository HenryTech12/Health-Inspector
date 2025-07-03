package com.healthhalo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    private String email;
    private String username;
    private String password;
    private Role role;
}
