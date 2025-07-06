package com.healthhalo.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyMedication {

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
    private String email;
}
