package com.github.microservice.auth.server.core.domain;

import com.github.microservice.components.data.mongo.mongo.domain.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends SuperEntity {

    @Indexed
    @DBRef(lazy = true)
    private User user;


}
