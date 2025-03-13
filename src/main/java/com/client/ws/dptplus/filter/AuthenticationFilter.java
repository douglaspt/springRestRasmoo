package com.client.ws.dptplus.filter;

import com.client.ws.dptplus.model.jpa.UserCredentials;
import com.client.ws.dptplus.repository.jpa.UserDetailsRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import com.client.ws.dptplus.exception.NotFoundException;

import com.client.ws.dptplus.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Objects;

public class AuthenticationFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UserDetailsRepository userDetailsRepository;

    public AuthenticationFilter(TokenService tokenService, UserDetailsRepository userDetailsRepository) {
        this.tokenService = tokenService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getBearerToken(request);
        if (tokenService.isValid(token)) {
            authByToken(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authByToken(String token) {

        //recuperar id do usuario
        Long userId = tokenService.getUserId(token);
        var userOpt = userDetailsRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        UserCredentials userCredentials = userOpt.get();
        //autenticar no spring

        UsernamePasswordAuthenticationToken userAuth
                = new UsernamePasswordAuthenticationToken(userCredentials, null, userCredentials.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(userAuth);
    }

    private String getBearerToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring(7);
    }

}
