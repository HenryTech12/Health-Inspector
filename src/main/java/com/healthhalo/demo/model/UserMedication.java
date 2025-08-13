package com.healthhalo.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String medicationName;
    private String prescribingDoctor;
    private String dosage;
    private String timeOfDay;
    private String startDate;
    private String frequency;
    private String endDate;
    private String medicationLocation;
    private String alertType;
    private String instructions;
    @OneToOne(fetch = FetchType.EAGER)
    private UserModel userModel;
}
