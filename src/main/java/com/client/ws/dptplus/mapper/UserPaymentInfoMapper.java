package com.client.ws.dptplus.mapper;

import com.client.ws.dptplus.dto.UserPaymentInfoDto;
import com.client.ws.dptplus.model.jpa.User;
import com.client.ws.dptplus.model.jpa.UserPaymentInfo;

public class UserPaymentInfoMapper {

    public static UserPaymentInfo fromDtoToEntity(UserPaymentInfoDto dto, User user){
        return UserPaymentInfo.builder()
                .id(dto.getId())
                .cardNumber(dto.getCardNumber())
                .cardExpirationMonth(dto.getCardExpirationMonth())
                .cardExpirationYear(dto.getCardExpirationYear())
                .cardSecurityCode(dto.getCardSecurityCode())
                .price(dto.getPrice())
                .dtpayment(dto.getDtpayment())
                .user(user)
                .build();
    }
}
