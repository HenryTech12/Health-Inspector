package com.healthhalo.demo.repository;

import com.healthhalo.demo.model.UserMedication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedRepository extends JpaRepository<UserMedication , Long> {


}
