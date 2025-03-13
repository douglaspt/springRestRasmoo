package com.client.ws.dptplus.configuration;

import com.client.ws.dptplus.filter.AuthenticationFilter;
import com.client.ws.dptplus.repository.jpa.UserDetailsRepository;
import com.client.ws.dptplus.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private static final String[] AUTH_SWAGGER_LIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/configuration/**",
            "/webjars/**"
    };

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Liberando os endpoints públicos
                        .requestMatchers(AUTH_SWAGGER_LIST).permitAll()
                        .requestMatchers(HttpMethod.GET, "/subscription-type").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user", "/payment/process", "/auth").permitAll()
                        .requestMatchers("/auth/recovery-code/**").permitAll()
                        // Exige autenticação para todas as outras requisições
                        .anyRequest().authenticated()
                )
                // Adiciona o filtro de autenticação customizado
                .addFilterBefore(new AuthenticationFilter(tokenService, userDetailsRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
