package config;

import com.fast.dev.acenter.annotation.EnableApplicationClient;
import com.github.microservice.core.boot.ApplicationBootSuper;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试代码的入口
 */

//需要使用此注解，且配置 application.properties

@EnableApplicationClient
@ComponentScan("config")
public class ConfigDemoApplication extends ApplicationBootSuper {


    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(ConfigDemoApplication.class, args);
    }


}
