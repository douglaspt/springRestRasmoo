package com.client.ws.dptplus.service.impl;


import com.client.ws.dptplus.dto.UserDetailsDto;
import com.client.ws.dptplus.exception.BadRequestException;
import com.client.ws.dptplus.exception.NotFoundException;
import com.client.ws.dptplus.integration.MailIntegration;
import com.client.ws.dptplus.model.jpa.UserCredentials;
import com.client.ws.dptplus.model.redis.UserRecoveryCode;
import com.client.ws.dptplus.repository.jpa.UserDetailsRepository;
import com.client.ws.dptplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.dptplus.service.UserDetailsService;
import com.client.ws.dptplus.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${webservices.dptplus.redis.recoverycode.timeout}")
    private String recoveryCodeTimeout;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Autowired
    private MailIntegration mailIntegration;

    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String pass) {

        var userCredentialsOpt = userDetailsRepository.findByUsername(username);

        if (userCredentialsOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        UserCredentials userCredentials = userCredentialsOpt.get();

        if (PasswordUtils.matches(pass, userCredentials.getPassword())) {
            return userCredentials;
        }

        throw new BadRequestException("Usuário ou senha inválido");
    }

    @Override
    public void sendRecoveryCode(String email) {
        UserRecoveryCode userRecoveryCode;
        String code = String.format("%04d", new Random().nextInt(10000));
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()) {

            var user = userDetailsRepository.findByUsername(email);
            if (user.isEmpty()) {
                throw new NotFoundException("Usuário não encontrado");
            }

            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);

        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }
        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreationDate(LocalDateTime.now());

        userRecoveryCodeRepository.save(userRecoveryCode);
        mailIntegration.send(email, "Código de recuperação de conta: "+code, "Código de recuperação de conta");

    }

    @Override
    public boolean recoveryCodeIsValid(String recoveryCode, String email) {
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        UserRecoveryCode userRecoveryCode = userRecoveryCodeOpt.get();

        LocalDateTime timeout = userRecoveryCode.getCreationDate().plusMinutes(Long.parseLong(recoveryCodeTimeout));
        LocalDateTime now = LocalDateTime.now();

        return recoveryCode.equals(userRecoveryCode.getCode()) && now.isBefore(timeout);
    }

    @Override
    public void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto) {

        if (recoveryCodeIsValid(userDetailsDto.getRecoveryCode(), userDetailsDto.getEmail())) {
            var userDetails = userDetailsRepository.findByUsername(userDetailsDto.getEmail());

            UserCredentials userCredentials = userDetails.get();

            userCredentials.setPassword(PasswordUtils.encode(userDetailsDto.getPassword()));

            userDetailsRepository.save(userCredentials);
        }
    }


}
