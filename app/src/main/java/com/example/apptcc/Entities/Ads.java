package com.example.apptcc.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Ads implements Parcelable {

    private String title;
    private String description;
    private String keyAds;
    private String uidAds;
    private String url;
    private String imageName;
    public Map<String, Boolean> ads = new HashMap<>();

    public Ads(){

    }

    public Ads(String title, String description, String keyAds, String url, String imageName) {
        this.title = title;
        this.description = description;
        this.keyAds = keyAds;
        this.url = url;
        this.imageName = imageName;
    }

    public Ads(String title, String description, String keyAds, String uidAds, String url, String imageName) {
        this.title = title;
        this.description = description;
        this.keyAds = keyAds;
        this.uidAds = uidAds;
        this.url = url;
        this.imageName = imageName;
    }

    protected Ads(Parcel in) {
        title = in.readString();
        description = in.readString();
        keyAds = in.readString();
        uidAds = in.readString();
        url = in.readString();
        imageName = in.readString();
    }

    public static final Creator<Ads> CREATOR = new Creator<Ads>() {
        @Override
        public Ads createFromParcel(Parcel in) {
            return new Ads(in);
        }

        @Override
        public Ads[] newArray(int size) {
            return new Ads[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(keyAds);
        dest.writeString(uidAds);
        dest.writeString(url);
        dest.writeString(imageName);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("keyAds", keyAds);
        result.put("uidAds", uidAds);
        result.put("url", url);
        result.put("imageName", imageName);
        result.put("ads", ads);

        return result;
    }

}
