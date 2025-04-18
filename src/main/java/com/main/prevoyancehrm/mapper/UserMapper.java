package com.main.prevoyancehrm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.main.prevoyancehrm.dto.ResponseDto.UserDetailResponse;
import com.main.prevoyancehrm.dto.ResponseDto.UserResponse;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDetailResponse toUserResponse(UserResponse userResponse){
        return this.modelMapper.map(userResponse, UserDetailResponse.class);
    }
    
}
