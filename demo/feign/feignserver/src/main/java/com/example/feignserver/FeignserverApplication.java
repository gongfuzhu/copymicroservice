package com.example.feignserver;

import com.fast.dev.acenter.annotation.EnableApplicationClient;
import com.github.microservice.core.boot.ApplicationBootSuper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableApplicationClient
@ComponentScan("com.example.feignserver.core")
public class FeignserverApplication extends ApplicationBootSuper {

    public static void main(String[] args) {
        SpringApplication.run(FeignserverApplication.class, args);
    }

}
