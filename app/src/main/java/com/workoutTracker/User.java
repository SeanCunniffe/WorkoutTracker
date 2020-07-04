package com.workoutTracker;

import java.time.LocalDate;

public class User {
    String name;
    LocalDate DOB;

    public User(String name, LocalDate DOB){
        this.name = name;
        this.DOB = DOB;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }
}
