package com.github.microservice.auth.server.core.dao;

import com.github.microservice.auth.server.core.domain.User;
import com.github.microservice.components.data.mongo.mongo.dao.MongoDao;

public interface UserDao extends MongoDao<User> {

    /**
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * @param idCard
     * @return
     */
    User findByIdcard(String idCard);

    /**
     * 通过邮箱查询
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

}
