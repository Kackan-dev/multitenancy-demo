package com.Kackan.multitenancy_demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final List<UserDetails> users = new ArrayList<>();

    public CustomUserDetailsService() {
        users.add(createUser("JOHNDOE", "JOHN123"));
        users.add(createUser("ANNADOE", "ANNA123"));
    }
    
    private UserDetails createUser(String name, String password) {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return User.builder()
                .username(name)
                .password(delegatingPasswordEncoder.encode(password))
                .build();
    }
    
    private UserDetails getUser(String username) {
        return users.stream()
                .filter(user ->  user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist!"));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }


}
