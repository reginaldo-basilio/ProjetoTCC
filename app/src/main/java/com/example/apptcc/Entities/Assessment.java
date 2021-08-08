package com.example.apptcc.Entities;

public class Assessment {

    private String date;
    private String comment;
    private float assessmentValue;
    private String keyAssessment;
    private String uidAssessment;

    public Assessment(){

    }

    public Assessment(String date, String comment, int assessmentValue, String keyAssessment, String uidAssessment) {
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

    public float getAssessmentValue() {
        return assessmentValue;
    }

    public void setAssessmentValue(int assessmentValue) {
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
