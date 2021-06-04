package com.example.feignserver.core.service;

import com.example.feignclient.model.User;
import com.example.feignclient.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Object put(User user) {
        return Map.of(
                "time", System.currentTimeMillis(),
                "user", user
        );
    }
}
