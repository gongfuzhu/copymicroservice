package com.github.microservice.auth.server.core.auth;

import com.github.microservice.auth.client.type.GrantType;
import com.github.microservice.auth.client.type.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthProcess {

    //登陆名
    private String loginName;

    //密码
    private String passWord;

    //类型
    private LoginType type;

    //类型
    private GrantType grantType;


    //参数
    private Map<String, String> parameters;


}
