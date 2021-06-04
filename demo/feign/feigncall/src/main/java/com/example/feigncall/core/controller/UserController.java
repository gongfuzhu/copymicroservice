package com.example.feigncall.core.controller;

import com.example.feignclient.model.User;
import com.example.feignclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("put")
    public Object put(User user) {
        return this.userService.put(user);
    }


    @RequestMapping("put2")
    public Object put2(User user) {
//        this.restTemplate.postForObject()
        return null;

//        return this.userService.put(user);
    }

}
