package com.github.microservice.gateway.boot;

import com.github.microservice.app.core.config.ConsulRegisterConfig;
import com.github.microservice.core.runner.BannerApplicationRunner;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@EnableDiscoveryClient
@ComponentScan("com.github.microservice.gateway.core")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import({ConsulRegisterConfig.class})
public class GatewayApplication {


    /**
     * 默认入口方法
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 启动成功后打印 Banner
     *
     * @return
     */
    @Bean
    public ApplicationRunner BannerApplicationRunner() {
        return new BannerApplicationRunner();
    }

}
