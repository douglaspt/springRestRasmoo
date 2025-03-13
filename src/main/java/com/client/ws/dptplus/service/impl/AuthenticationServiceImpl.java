package com.client.ws.dptplus.service.impl;

import com.client.ws.dptplus.dto.LoginDto;
import com.client.ws.dptplus.dto.TokenDto;
import com.client.ws.dptplus.exception.BadRequestException;
import com.client.ws.dptplus.model.jpa.UserCredentials;
import com.client.ws.dptplus.service.AuthenticationService;
import com.client.ws.dptplus.service.TokenService;
import com.client.ws.dptplus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Override
    public TokenDto auth(LoginDto dto) {
        try {
            UserCredentials userCredentials = userDetailsService.loadUserByUsernameAndPass(dto.getUsername(), dto.getPassword());
            String token = tokenService.getToken(userCredentials.getId());
            return TokenDto.builder().token(token).type("Bearer").build();
        } catch (Exception e) {
            throw new BadRequestException("Erro ao formatar token - "+e.getMessage());
        }
    }
}
