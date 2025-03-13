package com.client.ws.dptplus.mapper.wsraspay;

import com.client.ws.dptplus.dto.wsraspay.CustomerDto;
import com.client.ws.dptplus.model.jpa.User;

public class CustomerMapper {

    public static CustomerDto build(User user){
        var fullName = user.getName().split(" ");
        var firstName = fullName[0];
        var lastName = fullName.length > 1 ? fullName[fullName.length -1] : "";

        return CustomerDto.builder()
                .cpf(user.getCpf())
                .email(user.getEmail())
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
