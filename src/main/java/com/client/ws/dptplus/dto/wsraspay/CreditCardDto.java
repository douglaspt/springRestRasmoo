package com.client.ws.dptplus.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardDto {

    private String number;

    private Long cvv;

    private String documentNumber;

    private Long installments;

    private Long month;

    private Long year;

}
