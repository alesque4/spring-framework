package com.training.springcore;

import com.training.springcore.service.SiteService;
import com.training.springcore.service.SiteServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BigCorpApplication {

    public static void main (String[] args){
        BigCorpApplication application = new BigCorpApplication();
        application.run();
    }

    public void run(){
        //L'initialisation de siteService n'est faite qu'une fois
        //Avec scope="prototype" dans beans.xml, on peut l'instancier plusieurs fois
        //Avec lazy-init="true", l'instance n'est créée que quand on en a besoin (pendant le runtime)
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("Application startup");
        SiteService siteService = context.getBean(SiteService.class);
        System.out.println(siteService.findById("siteA"));
        SiteService siteService2 = context.getBean(SiteService.class);
        System.out.println(siteService2.findById("siteA"));
    }
}
