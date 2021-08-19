package com.example.apptcc.Entities;

public class Assessment {

    private String date;
    private String comment;
    private String assessmentValue;
    private String keyAssessment;
    private String uidAssessment;

    public Assessment(){

    }

    public Assessment(String date, String comment, String assessmentValue, String keyAssessment, String uidAssessment) {
        this.date = date;
        this.comment = comment;
        this.assessmentValue = assessmentValue;
        this.keyAssessment = keyAssessment;
        this.uidAssessment = uidAssessment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAssessmentValue() {
        return assessmentValue;
    }

    public void setAssessmentValue(String assessmentValue) {
        this.assessmentValue = assessmentValue;
    }

    public String getKeyAssessment() {
        return keyAssessment;
    }

    public void setKeyAssessment(String keyAssessment) {
        this.keyAssessment = keyAssessment;
    }

    public String getUidAssessment() {
        return uidAssessment;
    }

    public void setUidAssessment(String uidAssessment) {
        this.uidAssessment = uidAssessment;
    }
}
