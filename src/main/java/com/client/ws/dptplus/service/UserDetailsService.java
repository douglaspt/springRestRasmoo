package com.client.ws.dptplus.service;

import com.client.ws.dptplus.dto.UserDetailsDto;
import com.client.ws.dptplus.model.jpa.UserCredentials;

public interface UserDetailsService {

    UserCredentials loadUserByUsernameAndPass(String username, String pass);

    void sendRecoveryCode(String email);

    boolean recoveryCodeIsValid(String recoveryCode, String email);

    void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);
}
