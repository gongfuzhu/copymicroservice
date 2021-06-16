package com.github.microservice.auth.server.core.domain;

import com.github.microservice.components.data.mongo.mongo.domain.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User extends SuperEntity {

    //账号
    @Indexed(unique = true)
    private String userName;

    //手机号码
    @Indexed(unique = true)
    private String phone;

    //邮箱
    @Indexed(unique = true)
    private String email;

    //身份证
    @Indexed(unique = true)
    private String idcard;

    //密码
    private String passWord;


}
