package com.github.microservice.auth.server.core.dao;

import com.github.microservice.auth.server.core.domain.UserInfo;
import com.github.microservice.components.data.mongo.mongo.dao.MongoDao;

public interface UserInfoDao extends MongoDao<UserInfo> {
}
