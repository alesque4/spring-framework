package com.training.springcore.service;

import com.training.springcore.model.Site;

public class SiteServiceImpl implements SiteService {

    private CaptorService captorService;

    public SiteServiceImpl(CaptorService captorService){
        System.out.println("Init SiteServiceImpl :" + this);
        this.captorService = captorService;
    }

    @Override
    public Site findById(String siteId) {
        System.out.println("Appel de findById :" + this);

        if (siteId == null) {
            return null;
        }

        Site site = new Site("Florange");
        site.setId(siteId);
        site.setCaptors(captorService.findBySite(siteId));
        return site;
    }
}
