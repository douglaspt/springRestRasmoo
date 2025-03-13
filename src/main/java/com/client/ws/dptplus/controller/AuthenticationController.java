package com.client.ws.dptplus.controller;

import com.client.ws.dptplus.dto.LoginDto;
import com.client.ws.dptplus.dto.TokenDto;
import com.client.ws.dptplus.dto.UserDetailsDto;
import com.client.ws.dptplus.model.redis.UserRecoveryCode;
import com.client.ws.dptplus.service.AuthenticationService;
import com.client.ws.dptplus.service.UserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Operation(summary = "Realiza a autenticacao do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "Usuario e senha validados com sucesso"),
            @ApiResponse(responseCode  = "400", description  = "Usuario ou senha invalidos"),
            @ApiResponse(responseCode  = "404", description  = "Usuario nao encontrado")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.auth(dto));
    }

    @Operation(summary = "Envia codigo de recuperacao para o email do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "Codigo enviado com sucesso"),
            @ApiResponse(responseCode  = "400", description  = "Dados invalidos"),
            @ApiResponse(responseCode  = "404", description  = "Algum dado nao foi encontrado")
    })
    @PostMapping(value = "/recovery-code/send",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendRecoveryCode(@RequestBody @Valid UserRecoveryCode dto) {
        userDetailsService.sendRecoveryCode(dto.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(summary = "Valida codigo de recuperacao")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "Codigo de recuperacao e valido"),
            @ApiResponse(responseCode  = "400", description  = "Dados invalidos"),
            @ApiResponse(responseCode  = "404", description  = "Algum dado nao foi encontrado")
    })
    @GetMapping(value = "/recovery-code/")
    public ResponseEntity<?> recoveryCodeIsValid(@RequestParam("recoveryCode") String recoveryCode,
                                                 @RequestParam("email") String email) {
        return ResponseEntity.status(HttpStatus.OK).body( userDetailsService.recoveryCodeIsValid(recoveryCode, email));
    }

    @Operation(summary = "Atualiza senha a partir de um codigo de recuperacao valido")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode  = "400", description  = "Dados invalidos"),
            @ApiResponse(responseCode  = "404", description  = "Algum dado nao foi encontrado")
    })
    @PatchMapping(value = "/recovery-code/password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePasswordByRecoveryCode(@RequestBody @Valid UserDetailsDto dto) {
        userDetailsService.updatePasswordByRecoveryCode(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
