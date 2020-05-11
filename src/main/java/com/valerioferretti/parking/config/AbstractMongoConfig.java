package com.valerioferretti.parking.config;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public abstract class AbstractMongoConfig {

    public MongoDbFactory mongoDbFactory(String host, int port, String database){
        return new SimpleMongoDbFactory(new MongoClient(host, port), database);
    }

    abstract public MongoTemplate getMongoTemplate();
}
