package com.training.springcore;

import com.training.springcore.service.CaptorService;
import com.training.springcore.service.CaptorServiceImpl;
import com.training.springcore.service.SiteService;
import com.training.springcore.service.SiteServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BigCorpApplicationConfig {

    @Bean
    public SiteService serviceSite() {
        return new SiteServiceImpl(serviceCapteur());
    }
    @Bean
    public CaptorService serviceCapteur() {
        return new CaptorServiceImpl();
    }
}
