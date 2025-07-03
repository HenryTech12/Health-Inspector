package com.healthhalo.demo.response;

import com.healthhalo.demo.dto.Role;
import com.healthhalo.demo.dto.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String username;
    private String email;
    private Role role;
    private String jwtToken;
}
