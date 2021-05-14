package com.bekiruzun.todoapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    @Value("${application.db.username}")
    private String username;

    @Value("${application.db.password}")
    private String password;

    @Value("${application.db.bucket-name}")
    private String bucketName;

    @Override
    public String getConnectionString() {
        String couchbaseHost = System.getenv("COUCHBASE_HOST");
        if(couchbaseHost != null)
            return couchbaseHost; // running inside docker, variable contains dockerized couchbase service id
        return "couchbase://127.0.0.1"; // running outside docker
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
