package com.github.microservice.components.swagger.config;

import com.github.microservice.components.swagger.conf.SwaggerConf;
import com.github.microservice.core.mvc.MVCResponseConfiguration;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Vector;
import java.util.stream.Collectors;


@Log
//@EnableSwagger2
@EnableOpenApi
@Configuration
@Import(SwaggerConf.class)
public class SwaggerConfiguration implements ApplicationRunner {

    @Value("${server.port}")
    private int port;

    @Autowired
    private SwaggerConf swaggerConf;


    @Bean
    public Docket createRestApi() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerConf.build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerConf.getPackageName()))
                .paths(PathSelectors.any())
                .build();
        //全局参数配置
        if (this.swaggerConf.getGlobalRequestParameters() != null && this.swaggerConf.getGlobalRequestParameters().size() > 0) {
            docket.globalRequestParameters(this.swaggerConf.getGlobalRequestParameters().stream().map((it) -> {
                        return new RequestParameterBuilder().name(it.getName()).description(it.getDescription()).in(it.getParameterType()).build();
                    }).collect(Collectors.toList())
            );
        }

        return docket;
    }

    @Autowired
    private void setIgnoreTrans(ApplicationContext applicationContext) {
        Vector<String> ignoreTransformUrls = applicationContext.getBean(MVCResponseConfiguration.class).getIgnoreTransformUrls();
        ignoreTransformUrls.add("v2/api-docs");
        ignoreTransformUrls.add("v3/api-docs");
        ignoreTransformUrls.add("swagger-resources");
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String localhost = "http://localhost:" + port;
        log.info("DocApi UI : " + (localhost + "/swagger-ui/index.html"));
        log.info("PostMain Import : " + (localhost + "/v2/api-docs"));
        log.info("PostMain Import : " + (localhost + "/v3/api-docs"));
    }
}
