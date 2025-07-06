package com.healthhalo.demo.controller;

import com.healthhalo.demo.dto.MyMedication;
import com.healthhalo.demo.service.MedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/med")
public class MedController {

    @Autowired
    private MedService medService;

    @PostMapping("/create")
    public ResponseEntity<String> createUserMedication(@RequestBody MyMedication myMedication, Principal principal) {
        myMedication.setEmail(Objects.requireNonNull(principal).getName());
        medService.createMyMed(myMedication);
        return ResponseEntity.ok().body("user medications saved");
    }
}
