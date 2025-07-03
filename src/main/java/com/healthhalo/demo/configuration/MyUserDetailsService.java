package com.healthhalo.demo.configuration;

import com.healthhalo.demo.dto.UserData;
import com.healthhalo.demo.mapper.UserMapper;
import com.healthhalo.demo.model.UserModel;
import com.healthhalo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel =
                userRepository.findByUsername(username);
        UserData userData = userModel.map(
                data -> new UserData(data.getEmail(),data.getUsername(), data.getPassword(),data.getRole())
        ).orElse(new UserData());

        return new UserPrincipal(userData);
    }
}
