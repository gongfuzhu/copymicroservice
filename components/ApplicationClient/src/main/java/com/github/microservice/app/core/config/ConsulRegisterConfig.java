package com.github.microservice.app.core.config;

import com.ecwid.consul.v1.ConsulClient;
import com.github.microservice.app.helper.ConsulHelper;
import com.github.microservice.core.util.bytes.BytesUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.consul.ConsulAutoConfiguration;
import org.springframework.cloud.consul.ConsulProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulManagementRegistrationCustomizer;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

@Slf4j
@Configuration
public class ConsulRegisterConfig {

    @Value("${server.port}")
    private int port;

    @Value("$(spring.cloud.consul.discovery.preferIpAddress)")
    private String preferIpAddress;


    private final static File[] docker_host_file = new File[]{
            new File("/etc/docker_host_ip"),
            new File("c:/docker_host_ip")
    };


    @Bean
    public ConsulHelper consulHelper() {
        return new ConsulHelper();
    }


    /**
     * 构建客户端
     *
     * @param consulProperties
     * @return
     */
    @Bean
    public ConsulClient consulClient(ConsulProperties consulProperties, ConsulHelper consulHelper) {
        //取出最佳的主机
        ConsulHelper.HostItem hostItem = consulHelper.getPreferredHost(consulProperties);
        Optional.ofNullable(hostItem.getHost()).ifPresent((host) -> {
            consulProperties.setHost(host);
        });
        Optional.ofNullable(hostItem.getPort()).ifPresent((port) -> {
            consulProperties.setPort(port);
        });
        return ConsulAutoConfiguration.createConsulClient(consulProperties);
    }


    @Bean
    @SneakyThrows
    public ConsulAutoRegistration consulRegistration(
            AutoServiceRegistrationProperties autoServiceRegistrationProperties, ConsulDiscoveryProperties properties,
            ApplicationContext applicationContext,
            ObjectProvider<List<ConsulRegistrationCustomizer>> registrationCustomizers,
            ObjectProvider<List<ConsulManagementRegistrationCustomizer>> managementRegistrationCustomizers,
            HeartbeatProperties heartbeatProperties) {

        //ni.getInetAddresses().nextElement().getAddress();
        String mac = BytesUtil.binToHex(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress());
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        final Properties props = System.getProperties();
        String instance = (System.getProperty("os.name") + "-" + props.getProperty("java.version") + "-" + mac + "-" + time).replaceAll(" ", "").replaceAll("\\.", "");
        properties.setInstanceId(instance);


        //如果当前系统中存在配置文件的固定的ip地址则取系统ip地址
        // 如果配置为 prefer-ip-address: true 则不需要自定义ip
        if (!properties.isPreferIpAddress()) {
            Stream.of(docker_host_file).filter((file) -> {
                return file.exists();
            }).findFirst().ifPresent((it) -> {
                try {
                    String ip = FileUtils.readFileToString(it, "UTF-8").trim();
                    properties.setHostname(ip);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


        return ConsulAutoRegistration.registration(autoServiceRegistrationProperties, properties, applicationContext,
                registrationCustomizers.getIfAvailable(), managementRegistrationCustomizers.getIfAvailable(),
                heartbeatProperties);
    }


}
