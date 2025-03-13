package com.client.ws.dptplus.integration;

import com.client.ws.dptplus.dto.wsraspay.CreditCardDto;
import com.client.ws.dptplus.dto.wsraspay.CustomerDto;
import com.client.ws.dptplus.dto.wsraspay.OrderDto;
import com.client.ws.dptplus.dto.wsraspay.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class MailIntegrationTest {

    @Autowired
    private MailIntegration mailIntegration;

    @Test
    void sendMail(){
        mailIntegration.send("douglaspt@gmail.com","Ola! Essa é uma mensagem de teste",
                "Teste APP DPT");

    }

}
