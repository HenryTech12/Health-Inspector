package com.healthhalo.demo.mapper;

import com.healthhalo.demo.dto.MyMedication;
import com.healthhalo.demo.model.UserMedication;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class MedMapper {

    @Autowired
    private ModelMapper mapper;


    public MyMedication convertToDTO(UserMedication userMedication) {
        if(!Objects.isNull(userMedication))
            return mapper.map(userMedication, MyMedication.class);
        else
            return null;
    }

    public UserMedication convertToModel(MyMedication myMedication) {
        if(!Objects.isNull(myMedication))
            return mapper.map(myMedication, UserMedication.class);
        else
            return null;
    }
}
