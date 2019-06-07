package com.training.spring.bigcorp.config;

import com.training.spring.bigcorp.model.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Set;

@Configuration
public class BigCorpApplicationConfig {

    @Autowired
    private Environment environnement;

    @Bean
    public ApplicationInfo applicationInfo() {
        String name = environnement.getRequiredProperty("bigcorp.name");
        Integer version = environnement.getRequiredProperty("bigcorp.version", Integer.class);
        Set<String> emails = environnement.getRequiredProperty("bigcorp.emails", Set.class);
        String webSiteUrl = environnement.getRequiredProperty("bigcorp.webSiteUrl");
        return new ApplicationInfo(name, version, emails, webSiteUrl);
    }

}
