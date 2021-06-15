package com.github.microservice.auth.server.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // 登陆页
//                .formLogin().permitAll()
//                // 登出页
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
//                // 其余所有请求全部需要鉴权认证
//                .and().authorizeRequests().anyRequest().authenticated()
//                // 关闭csrf
//                .and().csrf().disable();


        http
                .csrf()
                .disable()
                .logout()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}