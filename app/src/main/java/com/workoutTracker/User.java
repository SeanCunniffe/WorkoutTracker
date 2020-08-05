package com.workoutTracker;

public class User {
    private String name;
    private String DOB;
    private long height;
    private long weight;
    private String sex;

    public User(String name, String DOB,long height,long weight,String sex){
        this.name = name;
        this.DOB = DOB;
        this.height = height;
        this.weight = weight;
        this.sex =sex;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
