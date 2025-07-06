package com.healthhalo.demo.service;

import com.healthhalo.demo.dto.UserData;
import com.healthhalo.demo.mapper.UserMapper;
import com.healthhalo.demo.model.UserModel;
import com.healthhalo.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(UserData userData) {
        if(!Objects.isNull(userData)) {
            UserModel userModel = userMapper.convertToModel(userData);
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
            userRepository.save(userModel);
            log.info("user with username: {} saved to db.",userData.getUsername());
        }
    }
    public UserData fetchDataByUsername(String username) {
        log.info("user data with username: {} fetched", username);
        return userMapper.convertToDTO(userRepository.
                findByUsername(username).orElse(new UserModel()));
    }

    public UserData fetchDataByEmail(String email) {
        log.info("user data with email: {} fetched", email);
        return userMapper.convertToDTO(userRepository.
                findByEmail(email).orElse(new UserModel()));
    }

    public void deleteUserDataByUsername(String username) {
        log.info("user data with username: {} removed from db",username);
        userRepository.deleteByUsername(username);
    }

    public UserData updateUser(String username, UserData newuserData) {
        //fetch data from db
        UserModel userModel =
                userRepository.findByUsername(username)
                        .orElse(new UserModel());
        UserModel newUserModel = userMapper.convertToModel(newuserData);
        newUserModel.setId(userModel.getId());

        userRepository.save(newUserModel); //updates user data
        log.info("user data with username: {} updated on db",username);
        return userMapper.convertToDTO(newUserModel);
    }

}
