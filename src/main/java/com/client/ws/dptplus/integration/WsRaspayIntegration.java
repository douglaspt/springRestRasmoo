package com.client.ws.dptplus.integration;

import com.client.ws.dptplus.dto.wsraspay.CustomerDto;
import com.client.ws.dptplus.dto.wsraspay.OrderDto;
import com.client.ws.dptplus.dto.wsraspay.PaymentDto;

public interface WsRaspayIntegration {

    CustomerDto createCustomer(CustomerDto dto);

    OrderDto createOrder(OrderDto dto);

    Boolean processPayment(PaymentDto dto);
}
