package com.github.microservice.app.core.config;

import com.ecwid.consul.v1.ConsulClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.consul.ConsulAutoConfiguration;
import org.springframework.cloud.consul.ConsulProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulManagementRegistrationCustomizer;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Configuration
public class ConsulRegisterConfig {

    private final static File[] docker_host_file = new File[]{
            new File("/etc/docker_host_ip"),
            new File("c:/docker_host_ip")
    };


    @Bean
    public ConsulAutoRegistration consulRegistration(
            AutoServiceRegistrationProperties autoServiceRegistrationProperties, ConsulDiscoveryProperties properties,
            ApplicationContext applicationContext,
            ObjectProvider<List<ConsulRegistrationCustomizer>> registrationCustomizers,
            ObjectProvider<List<ConsulManagementRegistrationCustomizer>> managementRegistrationCustomizers,
            HeartbeatProperties heartbeatProperties) {

        //如果当前系统中存在配置文件的固定的ip地址则取系统ip地址
        Stream.of(docker_host_file).filter((file) -> {
            return file.exists();
        }).findFirst().ifPresent((it) -> {
            try {
                properties.setHostname(FileUtils.readFileToString(it, "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return ConsulAutoRegistration.registration(autoServiceRegistrationProperties, properties, applicationContext,
                registrationCustomizers.getIfAvailable(), managementRegistrationCustomizers.getIfAvailable(),
                heartbeatProperties);
    }


}
