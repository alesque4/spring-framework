package com.training.springcore.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ApplicationInfo {

    private String name;

    private Integer version;

    private Set<String> emails;

    private String webSiteUrl;

    @Autowired
    public ApplicationInfo(String name, Integer version, Set<String> emails, String webSiteUrl) {
        this.name = name;
        this.version = version;
        this.emails = emails;
        this.webSiteUrl = webSiteUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getVersion() {
        return version;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public String getWebSiteUrl() {
        return webSiteUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }
}
