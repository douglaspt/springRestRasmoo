package com.client.ws.dptplus.controller;

import com.client.ws.dptplus.dto.PaymentProcessDto;
import com.client.ws.dptplus.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentInfoController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @PostMapping("/process")
    public ResponseEntity<Boolean> process(@RequestBody PaymentProcessDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(paymentInfoService.process(dto));
    }
}
