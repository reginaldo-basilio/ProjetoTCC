package com.example.apptcc.Entities;

public class Ads {

    private String title;
    private String description;
    private String keyAds;
    private String uidAds;

    public Ads(){

    }

    public Ads(String title, String description, String keyAds, String uidAds) {
        this.title = title;
        this.description = description;
        this.keyAds = keyAds;
        this.uidAds = uidAds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyAds() {
        return keyAds;
    }

    public void setKeyAds(String keyAds) {
        this.keyAds = keyAds;
    }

    public String getUidAds() {
        return uidAds;
    }

    public void setUidAds(String uidAds) {
        this.uidAds = uidAds;
    }
}
