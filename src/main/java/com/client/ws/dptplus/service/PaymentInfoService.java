package com.client.ws.dptplus.service;

import com.client.ws.dptplus.dto.PaymentProcessDto;

public interface PaymentInfoService {

    Boolean process(PaymentProcessDto dto);

}
