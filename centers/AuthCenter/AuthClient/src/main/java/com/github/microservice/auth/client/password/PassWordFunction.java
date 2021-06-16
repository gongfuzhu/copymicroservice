package com.github.microservice.auth.client.password;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PassWordFunction implements PasswordEncoder {


    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }
}
