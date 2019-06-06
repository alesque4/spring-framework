package com.training.springcore;

import com.training.springcore.model.ApplicationInfo;
import com.training.springcore.config.BigCorpApplicationConfig;
import com.training.springcore.service.SiteService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Collectors;

public class BigCorpApplication {

    public static void main (String[] args){
        BigCorpApplication application = new BigCorpApplication();
        application.run();
    }

    public void run(){
        ApplicationContext context =
                new AnnotationConfigApplicationContext(BigCorpApplicationConfig.class);

        ApplicationInfo applicationInfo = context.getBean(ApplicationInfo.class);
        System.out.println("==========================================================");
        System.out.println("Application [" + applicationInfo.getName() + "] - version : "
                + applicationInfo.getVersion());
        System.out.println("plus d'informations sur " + applicationInfo.getWebSiteUrl());
        System.out.println(applicationInfo.getEmails().stream().collect(Collectors.joining(", ")));
        System.out.println("==========================================================");

        SiteService siteService = context.getBean(SiteService.class);
        System.out.println(siteService.findById("siteA"));
    }
}
