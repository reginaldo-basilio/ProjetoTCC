package com.example.apptcc.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable {
    private int assessmentCounter;
    private String assessmentAvg;
    private int assessmentValue;
    private int counter;
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

    public User(String assessmentAvg, int counter, String fullName, String email, String contact, String fantasyName, String category, String state, String city, String district, String address, String number, String keyUser, String uid, String url) {
        this.assessmentAvg = assessmentAvg;
        this.counter = counter;
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

    protected User(Parcel in) {
        assessmentCounter = in.readInt();
        assessmentAvg = in.readString();
        assessmentValue = in.readInt();
        counter = in.readInt();
        fullName = in.readString();
        email = in.readString();
        contact = in.readString();
        fantasyName = in.readString();
        category = in.readString();
        state = in.readString();
        city = in.readString();
        district = in.readString();
        address = in.readString();
        number = in.readString();
        keyUser = in.readString();
        password = in.readString();
        uid = in.readString();
        url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getAssessmentCounter() {
        return assessmentCounter;
    }

    public void setAssessmentCounter(int assessmentCounter) {
        this.assessmentCounter = assessmentCounter;
    }

    public String getAssessmentAvg() {
        return assessmentAvg;
    }

    public void setAssessmentAvg(String assessmentAvg) {
        this.assessmentAvg = assessmentAvg;
    }

    public int getAssessmentValue() {
        return assessmentValue;
    }

    public void setAssessmentValue(int assessmentValue) {
        this.assessmentValue = assessmentValue;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
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
        result.put("assessmentCounter", assessmentCounter);
        result.put("assessmentAvg", assessmentAvg);
        result.put("assessmentValue", assessmentValue);
        result.put("counter", counter);
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(assessmentCounter);
        dest.writeString(assessmentAvg);
        dest.writeInt(assessmentValue);
        dest.writeInt(counter);
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(contact);
        dest.writeString(fantasyName);
        dest.writeString(category);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(address);
        dest.writeString(number);
        dest.writeString(keyUser);
        dest.writeString(password);
        dest.writeString(uid);
        dest.writeString(url);
    }
}
