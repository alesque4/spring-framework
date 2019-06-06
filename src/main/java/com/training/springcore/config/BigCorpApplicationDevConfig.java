package com.training.springcore.config;


import com.training.springcore.model.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.Set;

@Configuration
@Profile("!prod")
@PropertySource("classpath:application.properties")
public class BigCorpApplicationDevConfig {

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
