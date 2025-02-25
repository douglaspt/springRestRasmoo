package com.client.ws.dptplus.mapper;

import com.client.ws.dptplus.dto.SubscriptionTypeDto;
import com.client.ws.dptplus.model.SubscriptionType;

public class SubscriptionTypeMapper {

    public static SubscriptionType fromDtoToEntity(SubscriptionTypeDto dto){
        return SubscriptionType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .accessMonth(dto.getAccessMonth())
                .price(dto.getPrice())
                .productKey(dto.getProductKey())
                .build();

    }

}
