package com.training.spring.bigCorp;

import com.training.springcore.service.CaptorService;
import com.training.springcore.service.CaptorServiceImpl;
import com.training.springcore.service.SiteService;
import com.training.springcore.service.SiteServiceImpl;

public class ObjectFactory {

    public SiteService createSiteService() {
        return new SiteServiceImpl(createCaptorService());
    }

    public CaptorService createCaptorService() {
        return new CaptorServiceImpl();
    }
}
