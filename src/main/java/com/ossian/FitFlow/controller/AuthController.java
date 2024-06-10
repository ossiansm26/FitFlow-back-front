package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.security.CustomerDetailsService;
import com.ossian.FitFlow.security.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @GetMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        log.info("Verificando token: {}", token);
        try {
            String username = jwtUtil.extractUsername(token);
            log.info("Nombre de usuario extraído del token: {}", username);
            UserDetails userDetails = customerDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                log.info("Token válido para el usuario: {}", username);
                return ResponseEntity.ok("Token válido");
            } else {
                log.warn("Token inválido");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
        } catch (Exception e) {
            log.error("Error al validar el token: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }
}

