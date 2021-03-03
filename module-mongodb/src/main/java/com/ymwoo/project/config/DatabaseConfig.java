package com.ymwoo.project.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.ymwoo.project.*")
public class DatabaseConfig
{
    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    @PostConstruct
    public void init()
    {
        this.mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }





    @Bean
    @ConditionalOnMissingBean
    public GridFsTemplate getGridFsTemplate(MongoDbFactory mongoDatabaseFactory, MongoTemplate mongoTemplate)
    {
        return new GridFsTemplate(mongoDatabaseFactory, mongoTemplate.getConverter());
    }





    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory dbFactory)
    {
        return new MongoTransactionManager(dbFactory);
    }
}
