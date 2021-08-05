package com.example.apptcc.Entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String fullName;
    private String email;
    private String contact;
    private String fantasyName;
    private String category;
    private String state;
    private String city;
    private String district;
    private String address;
    private String number;
    private String keyUser;
    private String password;
    private String uid;
    private String url;
    public Map<String, Boolean> users = new HashMap<>();

    public User(){

    }

    public User(String fullName, String email, String contact, String fantasyName, String category, String state, String city, String district, String address, String number, String keyUser, String uid, String url) {
        this.fullName = fullName;
        this.email = email;
        this.contact = contact;
        this.fantasyName = fantasyName;
        this.category = category;
        this.state = state;
        this.city = city;
        this.district = district;
        this.address = address;
        this.number = number;
        this.keyUser = keyUser;
        this.uid = uid;
        this.url = url;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullName", fullName);
        result.put("email", email);
        result.put("contact", contact);
        result.put("fantasyName", fantasyName);
        result.put("category", category);
        result.put("state", state);
        result.put("city", city);
        result.put("district", district);
        result.put("address", address);
        result.put("number", number);
        result.put("keyUser", keyUser);
        result.put("uid", uid);
        result.put("url", url);
        result.put("users", users);

        return result;
    }
}
