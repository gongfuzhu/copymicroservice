package com.github.microservice.auth.server.core.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, Serializable {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User(s, "xiaofeng", List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "User";
            }
        }));
    }
}
