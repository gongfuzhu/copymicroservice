package com.github.microservice.auth.server.core.service;

import com.github.microservice.auth.client.type.LoginType;
import com.github.microservice.auth.server.core.auth.AuthHelper;
import com.github.microservice.auth.server.core.auth.AuthProcess;
import com.github.microservice.auth.server.core.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, Serializable {

    private static final long serialVersionUID = 1L;


    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String loginValue) throws UsernameNotFoundException {
        final AuthProcess authProcess = authHelper.getAuthProcess();
        com.github.microservice.auth.server.core.domain.User user = null;
        if (authProcess.getType() == LoginType.Phone) {
            user = this.userDao.findByPhone(loginValue);
        } else if (authProcess.getType() == LoginType.UserName) {
            user = this.userDao.findByUserName(loginValue);
        } else if (authProcess.getType() == LoginType.Email) {
            user = this.userDao.findByEmail(loginValue);
        } else if (authProcess.getType() == LoginType.IdCard) {
            user = this.userDao.findByIdcard(loginValue);
        }
        Assert.state(user != null, "账号不存在");

        return new User(loginValue, user.getPassWord(), List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "User";
            }
        }));
    }
}
