package com.ossian.FitFlow.security;

import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.ossian.FitFlow.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Iniciando la carga del usuario por nombre de usuario: {}", username);

        user = userRepository.findByEmail(username);
        if (user != null) {
            log.info("Usuario encontrado: {}", username);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        } else {
            log.warn("Usuario no encontrado con nombre de usuario: {}", username);
            throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + username);
        }
    }

    public User getUser() {
        log.info("Obteniendo el usuario actual: {}", user != null ? user.getEmail() : "No user found");
        return user;
    }
}
