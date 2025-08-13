package com.healthhalo.demo.request;

import lombok.Data;

@Data
public class Reminder {
    private String user;
    private String medicine;
    private String time;
}
