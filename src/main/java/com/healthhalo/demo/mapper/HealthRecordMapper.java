package com.healthhalo.demo.mapper;

import com.healthhalo.demo.model.HealthRecordModel;
import com.healthhalo.demo.request.HealthRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HealthRecordMapper {

    private ModelMapper mapper;

    public HealthRecord convertToDTO(HealthRecordModel healthRecordModel) {
        if(!Objects.isNull(healthRecordModel))
            return mapper.map(healthRecordModel, HealthRecord.class);
        else
            return null;
    }

    public HealthRecordModel convertToModel(HealthRecord healthRecord) {
        if(!Objects.isNull(healthRecord))
            return mapper.map(healthRecord, HealthRecordModel.class);
        else
            return null;
    }
}
