package com.training.springcore;

import com.training.springcore.model.Captor;
import com.training.springcore.model.MeasureStep;
import com.training.springcore.service.CaptorService;
import com.training.springcore.service.SiteService;
import com.training.springcore.service.measure.MeasureService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Instant;

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


    }
}
