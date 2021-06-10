package com.example.mongo.core.config;

import com.github.microservice.components.data.mongo.mongo.config.MongoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Import(MongoConfiguration.class)
@EnableMongoRepositories("com.example.mongo.core.dao")
public class MongoConfig {
}
