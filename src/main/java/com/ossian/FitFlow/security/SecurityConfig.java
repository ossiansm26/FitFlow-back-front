package com.ossian.FitFlow.security;

import com.ossian.FitFlow.security.jwt.JwtFilter;
import com.ossian.FitFlow.security.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.HttpSecurityDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    private final CustomerDetailsService customerDetailsService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(CustomerDetailsService customerDetailsService, JwtFilter jwtFilter) {
        log.info("Instanciando SecurityConfig con CustomerDetailsService y JwtFilter.");
        this.customerDetailsService = customerDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Creando PasswordEncoder.");
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Configurando SecurityFilterChain.");
        httpSecurity.cors().configurationSource(request -> {
                    log.info("Configurando políticas de CORS.");
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://localhost:8080");
                    corsConfig.addAllowedMethod("*");
                    corsConfig.addAllowedHeader("*");
                    corsConfig.setAllowCredentials(true);
                    log.info("Políticas de CORS configuradas: {}", corsConfig);
                    return corsConfig;
                })
                .and()
                .csrf().disable()
                .authorizeHttpRequests(authorizeRequests -> {
                    log.info("Configurando rutas permitidas sin autenticación.");
                    authorizeRequests

                            .requestMatchers(
                                    "/api/user/login",
                                    "/api/user/userLogin",
                                    "/api/user/createUser",
                                    "/api/user/getAllUser",
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/api/file/download/**",
                                    "/api/file/download",
                                    "/api/file/upload",
                                    "/chat-socket/**",
                                    "/api/auth/verifyToken"
                            ).permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement(sessionManagement -> {
                    log.info("Configurando la gestión de sesiones sin estado.");
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        log.info("Añadiendo JwtFilter antes de UsernamePasswordAuthenticationFilter.");
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("SecurityFilterChain configurado correctamente.");
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        log.info("Creando AuthenticationManager.");
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        log.info("AuthenticationManager creado: {}", authenticationManager);
        return authenticationManager;
    }
}

