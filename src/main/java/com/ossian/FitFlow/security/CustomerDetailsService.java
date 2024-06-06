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
    private  UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username: " + username);
        user = userRepository.findByEmail(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public User getUser() {
        return user;
    }
}
