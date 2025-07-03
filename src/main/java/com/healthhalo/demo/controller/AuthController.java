package com.healthhalo.demo.controller;

import com.healthhalo.demo.dto.UserData;
import com.healthhalo.demo.request.AuthRequest;
import com.healthhalo.demo.response.AuthResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        return "logged in";
    }

    @GetMapping("/token")
    public AuthResponse getJwtToken(@RequestBody AuthRequest authRequest) {
        System.out.println("hello bro");
        return null;
    }

}
