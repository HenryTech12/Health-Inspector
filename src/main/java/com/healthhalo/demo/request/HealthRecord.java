package com.healthhalo.demo.request;

import lombok.Data;

import java.util.List;

@Data
public class HealthRecord {
    private String name;
    private int age;
    private List<String> allergies;
    private List<String> medical_history;
}
