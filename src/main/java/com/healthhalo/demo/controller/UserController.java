package com.healthhalo.demo.controller;

import com.healthhalo.demo.dto.UserData;
import com.healthhalo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserData userData) {
        System.out.println("hello");
        userService.addUser(userData);
        return new ResponseEntity<>("user data successfully added to db.", HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserData> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok()
                .body(userService.fetchDataByUsername(username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> removeUserByUsername(@PathVariable String username) {
        userService.deleteUserDataByUsername(username);
        return ResponseEntity.ok()
                .body("User Data Removed from db successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<UserData> updateUserData(Principal principal, UserData userData) {
        String username = null;
        if(!Objects.isNull(principal))
            username = principal.getName();
        UserData result = userService.updateUser(username,userData);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
