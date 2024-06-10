package com.ossian.FitFlow.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Slf4j
@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;

    public String extractUsername(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("El token JWT no puede ser nulo o vacío");
        }
        log.info("Extrayendo nombre de usuario del token.");
        String username = extractClaim(token, Claims::getSubject);
        log.info("Nombre de usuario extraído: {}", username);
        return username;
    }

    public Date extractExpiration(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("El token JWT no puede ser nulo o vacío");
        }
        log.info("Extrayendo fecha de expiración del token.");
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        log.info("Fecha de expiración extraída: {}", expirationDate);
        return expirationDate;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("El token JWT no puede ser nulo o vacío");
        }
        log.info("Extrayendo reclamación del token.");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("El token JWT no puede ser nulo o vacío");
        }
        log.info("Extrayendo todas las reclamaciones del token.");
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("El token JWT no puede ser nulo o vacío");
        }
        log.info("Verificando si el token ha expirado.");
        Date expirationDate = extractExpiration(token);
        Boolean isExpired = expirationDate.before(new Date());
        log.info("¿Token expirado?: {}", isExpired);
        return isExpired;
    }

    public String generateToken(String username) {
        log.info("Generando nuevo token para el usuario: {}", username);
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, username);
        log.info("Token generado: {}", token);
        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        log.info("Creando token con las reclamaciones: {}", claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de validez
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("El token JWT no puede ser nulo o vacío");
        }
        log.info("Validando el token para el usuario: {}", userDetails.getUsername());
        final String username = extractUsername(token);
        Boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        log.info("¿Token válido?: {}", isValid);
        return isValid;
    }
}
