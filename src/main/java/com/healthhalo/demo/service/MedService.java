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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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

    public String checkForSymptoms(SymptomInput data) throws URISyntaxException {
        if(!Objects.isNull(data)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(data);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            HttpEntity<?> http = new HttpEntity<>(body);
            System.out.println(ai_url);
            return restTemplate.exchange(new URI(ai_url+"/check_symptoms"), HttpMethod.POST,http,String.class).getBody();
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
                response = restTemplate.exchange(new URI(ai_url+"/record_health"), HttpMethod.POST, http, String.class).getBody();
                return response;
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } finally {
                healthRecordRepository.save(healthRecordMapper.convertToModel(healthRecord));
                log.info(response);
                System.out.println("Response: "+response);
            }
        }
        return null;
    }

    public String getHealthTip() throws URISyntaxException {
        System.out.println(ai_url);
        return  restTemplate.getForEntity(new URI(ai_url+"/health_tip"),String.class).getBody();
    }

    public String setReminder(Reminder reminder) throws URISyntaxException {
        if(!Objects.isNull(reminder)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(reminder);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            HttpEntity<?> http = new HttpEntity<>(body);
            return restTemplate.exchange(new URI(ai_url+"/set_reminder"), HttpMethod.POST,http,String.class).getBody();
        }
        return null;
    }

    public String bookAppointment(Appointment appointment) throws URISyntaxException {
        if(!Objects.isNull(appointment)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(appointment);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            HttpEntity<?> http = new HttpEntity<>(body);
            return restTemplate.exchange(new URI(ai_url+"/book_appointment"), HttpMethod.POST,http,String.class).getBody();
        }
        return null;
    }

    public String mentalHealthCheck(int score) throws URISyntaxException {
        String body = null;
        try {
            body = objectMapper.writeValueAsString(score);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpEntity<?> http = new HttpEntity<>(body);
        return restTemplate.exchange(new URI(ai_url+"/mental_health_check"), HttpMethod.POST,http,String.class).getBody();
    }

    public String aiQuery(AIRequest data) throws URISyntaxException {
        if(!Objects.isNull(data)) {
            String body = null;
            try {
                body = objectMapper.writeValueAsString(data);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            HttpEntity<?> http = new HttpEntity<>(body);
            return restTemplate.exchange(new URI(ai_url+"/ai-query"), HttpMethod.POST,http,String.class).getBody();
        }
        return null;
    }
}


