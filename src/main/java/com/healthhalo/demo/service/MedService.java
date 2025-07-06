package com.healthhalo.demo.service;

import com.healthhalo.demo.dto.MyMedication;
import com.healthhalo.demo.mapper.MedMapper;
import com.healthhalo.demo.repository.MedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class MedService {

    @Autowired
    private MedRepository medRepository;
    @Autowired
    private MedMapper medMapper;

    public void createMyMed(MyMedication myMedication) {
        if(!Objects.isNull(myMedication)) {
            medRepository.save(medMapper.convertToModel(myMedication));
            log.info("user medication saved to db");
        }
    }
}
