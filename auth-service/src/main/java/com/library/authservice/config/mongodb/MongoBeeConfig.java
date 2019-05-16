package com.library.authservice.config.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.mongobee.Mongobee;

@Configuration
@DependsOn("mongoTemplate")
public class MongoBeeConfig {

    private static final String MONGODB_URL_FORMAT = "mongodb://%s:%s@%s:%d/%s";
    private static final String MONGODB_CHANGELOGS_PACKAGE = "com.library.authservice.config.mongodb.changelogs";

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Mongobee mongobee() {
        String uri = String.format(MONGODB_URL_FORMAT,
                mongoProperties.getUsername(),
                String.valueOf(mongoProperties.getPassword()),
                mongoProperties.getHost(),
                mongoProperties.getPort(),
                mongoProperties.getDatabase());
        
        System.out.println(uri);
        
		Mongobee runner = new Mongobee(uri);
        runner.setMongoTemplate(mongoTemplate);
        runner.setDbName(mongoProperties.getDatabase());
        runner.setChangeLogsScanPackage(MONGODB_CHANGELOGS_PACKAGE);
        return runner;
    }

}