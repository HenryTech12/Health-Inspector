package com.healthhalo.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthhalo.demo.dto.MyMedication;
import com.healthhalo.demo.mapper.HealthRecordMapper;
import com.healthhalo.demo.mapper.MedMapper;
import com.healthhalo.demo.repository.HealthRecordRepository;
import com.healthhalo.demo.repository.MedRepository;
import com.healthhalo.demo.request.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
public class MedService {

    @Autowired
    private MedRepository medRepository;
    @Autowired
    private MedMapper medMapper;

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Value("${ai_backend_url}")
    private String ai_url;

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void createMyMed(MyMedication myMedication) {
        if(!Objects.isNull(myMedication)) {
            medRepository.save(medMapper.convertToModel(myMedication));
            log.info("user medication saved to db");
        }
    }

    public String checkForSymptoms(SymptomInput data) {
        if(!Objects.isNull(data)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(data);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            HttpEntity<?> http = new HttpEntity<>(body);
            return restTemplate.exchange(ai_url+"/check_symptoms", HttpMethod.POST,http,String.class).getBody();
        }
        return null;
    }

    public String saveHealthRecord(HealthRecord healthRecord) {
        if(!Objects.isNull(healthRecord)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(healthRecord);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            String response = "";
            try {
                HttpEntity<?> http = new HttpEntity<>(body);
                response = restTemplate.exchange(ai_url + "/record_health", HttpMethod.POST, http, String.class).getBody();
                return response;
            }
            finally {
                healthRecordRepository.save(healthRecordMapper.convertToModel(healthRecord));
                log.info(response);
                System.out.println("Response: "+response);
            }
        }
        return null;
    }

    public String getHealthTip() {
        return  restTemplate.getForEntity(ai_url+"/health_tip",String.class).getBody();
    }

    public String setReminder(Reminder reminder) {
        if(!Objects.isNull(reminder)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(reminder);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            HttpEntity<?> http = new HttpEntity<>(body);
            return restTemplate.exchange(ai_url+"/book_appointment", HttpMethod.POST,http,String.class).getBody();
        }
        return null;
    }

    public String bookAppointment(Appointment appointment) {
        if(!Objects.isNull(appointment)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(appointment);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            HttpEntity<?> http = new HttpEntity<>(body);
            return restTemplate.exchange(ai_url+"/book_appointment", HttpMethod.POST,http,String.class).getBody();
        }
        return null;
    }

    public String mentalHealthCheck(int score) {
        String body = null;
        try {
            body = objectMapper.writeValueAsString(score);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpEntity<?> http = new HttpEntity<>(body);
        return restTemplate.exchange(ai_url+"/book_appointment", HttpMethod.POST,http,String.class).getBody();
    }

    public String aiQuery(AIRequest data) {
        if(!Objects.isNull(data)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(data);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            HttpEntity<?> http = new HttpEntity<>(body);
            return restTemplate.exchange(ai_url+"/ai-query", HttpMethod.POST,http,String.class).getBody();
        }
        return null;
    }
}


