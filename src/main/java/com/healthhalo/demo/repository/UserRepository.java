package com.healthhalo.demo.repository;

import com.healthhalo.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
    @Transactional
    @Modifying
    void deleteByUsername(String username);
}
