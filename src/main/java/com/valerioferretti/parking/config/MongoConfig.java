package com.valerioferretti.parking.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig extends AbstractMongoConfig {

    private Logger log = LoggerFactory.getLogger(MongoConfig.class);

    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.database}")
    private String database;

    @Value("${mongodb.port}")
    private int port;

    @Primary
    @Override
    public @Bean(name="MongoTemplate") MongoTemplate getMongoTemplate() {

        log.info("MongoTemplate - Host: {}, Port: {}, Database: {}", host, port, database);

        return new MongoTemplate(mongoDbFactory(host, port, database));
    }
}