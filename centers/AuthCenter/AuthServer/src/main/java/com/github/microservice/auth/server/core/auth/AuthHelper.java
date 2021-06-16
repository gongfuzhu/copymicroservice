package com.github.microservice.auth.server.core.auth;

import com.github.microservice.auth.client.type.GrantType;
import com.github.microservice.auth.client.type.LoginType;
import com.github.microservice.auth.server.core.conf.AuthConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthHelper {


    @Autowired
    private AuthConf authConf;

    //线程容器
    private ThreadLocal<AuthProcess> threadLocal = new ThreadLocal();


    /**
     * 放入当前线程
     *
     * @param parameters
     */
    public void put(Map<String, String> parameters) {
        AuthProcess authProcess = AuthProcess
                .builder()
                .grantType(GrantType.valueOf(parameters.get("grant_type")))
                .parameters(parameters)
                .loginName(parameters.get("username"))
                .passWord(parameters.get("password"))
                .type(LoginType.valueOf(parameters.getOrDefault("type", authConf.getDefaultLoginType().name())))
                .build();
        this.threadLocal.set(authProcess);
    }


    /**
     * 取出校验密码
     *
     * @return
     */
    public AuthProcess getAuthProcess() {
        return this.threadLocal.get();
    }


    /**
     * 释放
     */
    public void release() {
        this.threadLocal.remove();
    }


}
