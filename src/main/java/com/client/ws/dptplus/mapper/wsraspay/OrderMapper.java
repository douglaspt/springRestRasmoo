package com.client.ws.dptplus.mapper.wsraspay;

import com.client.ws.dptplus.dto.PaymentProcessDto;
import com.client.ws.dptplus.dto.wsraspay.OrderDto;

public class OrderMapper {

    public static OrderDto build(String customerId, PaymentProcessDto paymentProcessDto) {
        return OrderDto.builder()
                .customerId(customerId)
                .productAcronym(paymentProcessDto.getProductKey())
                .discount(paymentProcessDto.getDiscount())
                .build();
    }
}
