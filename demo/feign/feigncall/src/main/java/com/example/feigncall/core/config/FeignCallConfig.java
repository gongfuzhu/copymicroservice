package com.example.feigncall.core.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.example.feignclient.service")
public class FeignCallConfig {
}
