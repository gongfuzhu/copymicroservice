package com.github.microservice.app.helper;

import com.github.microservice.core.util.JsonUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.consul.ConsulProperties;
import org.springframework.util.Assert;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
public class ConsulHelper {


    /**
     * 获取最佳的主机
     *
     * @return
     */
    public HostItem getPreferredHost(ConsulProperties consulProperties) {
        String hosts = consulProperties.getHost();
        Assert.hasText(hosts, "注册不能为空");
        if (hosts.indexOf(",") == -1) {
            return HostItem.builder().host(hosts).build();
        }
        //多个ip则进行分割
        Set<HostItem> hostItems = Set.of(hosts.split(","))
                .stream()
                .map((it) -> {
                    String[] hostSplit = it.split(":");
                    if (hostSplit.length == 0) {
                        return HostItem.builder().host(hostSplit[0]).build();
                    } else if (hostSplit.length == 1) {
                        return HostItem.builder().host(hostSplit[0]).port(consulProperties.getPort()).build();
                    } else if (hostSplit.length >= 1) {
                        return HostItem.builder().host(hostSplit[0]).port(Integer.parseInt(hostSplit[1])).build();
                    }
                    return null;
                }).filter(it -> it != null)
                .collect(Collectors.toSet());


        //选择最佳主机
        return getPreferredHost(hostItems);
    }


    /**
     * 取出最佳主机
     *
     * @param hostItems
     * @return
     */
    private HostItem getPreferredHost(Set<HostItem> hostItems) {
        AtomicReference<HostItem> atomicReference = new AtomicReference<>();
        hostItems.parallelStream().map((it) -> {
            try {
                Map services = getServices(it);
                HostService hostService = new HostService();
                BeanUtils.copyProperties(it, hostService);
                hostService.setServiceCount(services.size());
                return hostService;
            } catch (Exception e) {
                log.error("consul : {} -> {}", it.getHost() + ":" + it.getPort(), e.getMessage());
            }
            return null;
        }).filter(it -> it != null).sorted((it1, it2) -> {
            return it1.getServiceCount() - it2.getServiceCount();
        }).findFirst().ifPresent((it) -> {
            atomicReference.set(it);
        });
        return atomicReference.get();
    }


    /**
     * 取出业务
     *
     * @param hostItem
     * @return
     */
    @SneakyThrows
    private Map getServices(HostItem hostItem) {
        String url = String.format("http://%s:%s/v1/agent/services", hostItem.getHost(), hostItem.getPort());
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .timeout(Duration.of(3, ChronoUnit.SECONDS))
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonUtil.toObject(response.body(), Map.class);
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class HostService extends HostItem {

        private Integer serviceCount;

    }


    @Data
    @Builder
    @NoArgsConstructor
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class HostItem {
        private String host;
        private Integer port;
    }


}
