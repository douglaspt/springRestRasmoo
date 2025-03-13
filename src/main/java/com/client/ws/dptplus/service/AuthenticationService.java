package com.client.ws.dptplus.service;

import com.client.ws.dptplus.dto.LoginDto;
import com.client.ws.dptplus.dto.TokenDto;

public interface AuthenticationService {

    TokenDto auth(LoginDto dto);

}
