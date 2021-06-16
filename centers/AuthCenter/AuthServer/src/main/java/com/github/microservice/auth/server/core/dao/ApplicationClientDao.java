package com.github.microservice.auth.server.core.dao;

import com.github.microservice.auth.server.core.domain.ApplicationClient;
import com.github.microservice.components.data.mongo.mongo.dao.MongoDao;

public interface ApplicationClientDao extends MongoDao<ApplicationClient> {

    /**
     * 通过客户端id查询
     *
     * @param clientId
     * @return
     */
    ApplicationClient findByClientId(String clientId);

}
