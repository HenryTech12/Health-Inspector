package com.healthhalo.demo.controller;

import com.healthhalo.demo.dto.MyMedication;
import com.healthhalo.demo.request.*;
import com.healthhalo.demo.service.MedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
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

    @PostMapping("/check_symptoms")
    public ResponseEntity<String> checkforSymptoms(@RequestBody  SymptomInput data) throws URISyntaxException {
        return new ResponseEntity<>(medService.checkForSymptoms(data), HttpStatus.OK);
    }

    @GetMapping("/health_tip")
    public ResponseEntity<String> getHealthTip() throws URISyntaxException {
        return new ResponseEntity<>(medService.getHealthTip(),HttpStatus.OK);
    }

    @PostMapping("/set_reminder")
    public ResponseEntity<String> setReminder(Reminder reminder) throws URISyntaxException {
        return new ResponseEntity<>(medService.setReminder(reminder),HttpStatus.OK);
    }

    @PostMapping("/book_appointment")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) throws URISyntaxException {
        return new ResponseEntity<>(medService.bookAppointment(appointment),HttpStatus.OK);
    }

    @PostMapping("/mental_health_check")
    public ResponseEntity<String> getMentalCheck(@RequestBody int score) throws URISyntaxException {
        return new ResponseEntity<>(medService.mentalHealthCheck(score),HttpStatus.OK);
    }

    @PostMapping("/record_health")
    public ResponseEntity<String> addHealthRecord(HealthRecord record) {
        return new ResponseEntity<>(medService.saveHealthRecord(record),HttpStatus.OK);
    }

    @PostMapping("/ai/query")
    public ResponseEntity<String> aiQuery(AIRequest data) throws URISyntaxException {
        return new ResponseEntity<>(medService.aiQuery(data),HttpStatus.OK);
    }
}
