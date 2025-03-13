package com.client.ws.dptplus.integration;

import com.client.ws.dptplus.dto.wsraspay.CustomerDto;
import com.client.ws.dptplus.integration.impl.WsRaspayIntegrationImpl;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WsRaspayIntegrationImplTest {

    @Mock
    private RestTemplate restTemplate;

    private static HttpHeaders headers;

    @InjectMocks
    private WsRaspayIntegrationImpl wsRaspayIntegration;

    @BeforeAll
    public static void loadHeaders(){
        headers = getHttpHeaders();
    }

    @Test
    void given_createCustomer_when_apiResponseIs201Created_then_returnCustomerDto(){
        ReflectionTestUtils.setField(wsRaspayIntegration,"raspayHost", "http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration,"customerUrl", "/customer");
        CustomerDto dto = new CustomerDto();
        dto.setCpf("11122233344");
        HttpEntity<CustomerDto> request = new HttpEntity<>(dto, this.headers);
        when(restTemplate.exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class))
                .thenReturn(ResponseEntity.of(Optional.of(dto)));
        wsRaspayIntegration.createCustomer(dto);
        verify(restTemplate, times(1))
                .exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class);
    }



    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credential = "rasmooplus:r@sm00";
        String base64 = new String(Base64.encodeBase64(credential.getBytes(), false));
        headers.add("Authorization", "Basic " + base64);
        return headers;
    }





/*

    @Test
    void createCustomerWhenDtoOk(){
        CustomerDto dto = new CustomerDto(null,"84760044000","teste@gmail.com","Aroldo","Carvalho");
        wsRaspayIntegration.createCustomer(dto);
    }

    @Test
    void createOrderWhenDtoOk(){
        OrderDto dto = new OrderDto(null,"67bdb6826a60c81e77a770df", BigDecimal.ZERO,"MONTH22");
        wsRaspayIntegration.createOrder(dto);
    }

    @Test
    void processPaymentWhenDtoOk(){
        CreditCardDto creditCardDto = new CreditCardDto("1223444455556666",222L,"84760044000",0L,10L,2028L);
        PaymentDto paymentDto = new PaymentDto(creditCardDto,"67bdb6826a60c81e77a770df","67bdd88d6a60c81e77a770e4");
        wsRaspayIntegration.processPayment(paymentDto);
    }
*/

}
