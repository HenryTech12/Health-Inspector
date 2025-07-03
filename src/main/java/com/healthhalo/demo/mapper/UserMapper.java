package com.healthhalo.demo.mapper;

import com.healthhalo.demo.dto.UserData;
import com.healthhalo.demo.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserMapper {

    @Autowired
    private ModelMapper mapper;


    public UserData convertToDTO(UserModel userModel){
        if(!Objects.isNull(userModel))
            return mapper.map(userModel, UserData.class);
        else
            return null;
    }

    public UserModel convertToModel(UserData userData) {
        if(!Objects.isNull(userData))
            return mapper.map(userData, UserModel.class);
        else
            return null;
    }
}
