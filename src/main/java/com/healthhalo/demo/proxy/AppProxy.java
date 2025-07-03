package com.healthhalo.demo.proxy;

import com.healthhalo.demo.service.JwtService;
import com.healthhalo.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@NoArgsConstructor
@Data
public class AppProxy {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    public AppProxy(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }
}
