package com.training.springcore;

import com.training.springcore.service.SiteService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BigCorpApplication {

    public static void main (String[] args){
        BigCorpApplication application = new BigCorpApplication();
        application.run();
    }

    public void run(){
        /*
            Les annotations @Scope et @Lazy fonctionnent de la même manière
            avec la configuration automatique
         */
        ApplicationContext context = new
            AnnotationConfigApplicationContext(BigCorpApplicationConfig.class);
        System.out.println("Application startup");
        SiteService siteService = context.getBean(SiteService.class);
        System.out.println(siteService.findById("siteA"));
        SiteService siteService2 = context.getBean(SiteService.class);
        System.out.println(siteService2.findById("siteA"));

        siteService.readFile("classpath:lorem.txt");

    }
}
