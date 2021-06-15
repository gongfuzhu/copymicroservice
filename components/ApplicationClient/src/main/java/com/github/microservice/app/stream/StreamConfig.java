package com.github.microservice.app.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamConfig {

    @Bean
    public StreamHelper streamHelper() {
        return new StreamHelper();
    }


}
