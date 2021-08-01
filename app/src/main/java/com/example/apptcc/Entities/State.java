package com.example.apptcc.Entities;

public class State {
    private String id;
    private String initials;
    private String name;

    public State(){

    }
    public State(String id, String initials, String name) {
        this.id = id;
        this.initials = initials;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return (this.getId());
    }
}
