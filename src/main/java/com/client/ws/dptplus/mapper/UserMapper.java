package com.client.ws.dptplus.mapper;

import com.client.ws.dptplus.dto.UserDto;
import com.client.ws.dptplus.model.SubscriptionType;
import com.client.ws.dptplus.model.User;
import com.client.ws.dptplus.model.UserType;

public class UserMapper {

    public static User fromDtoToEntity(UserDto dto, UserType userType, SubscriptionType subscriptionType){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .dtSubscription(dto.getDtSubscription())
                .dtExpiration(dto.getDtExpiration())
                .userType(userType)
                .subscriptionType(subscriptionType)
                .build();
    }
}
