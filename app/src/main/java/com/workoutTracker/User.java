package com.workoutTracker;

public class User {
    private String name;
    private String DOB;
    private Double height;
    private Long weight;
    private String sex;
    private String username;

    public User(String name, String DOB,Double height,Long weight,String sex,String username){
        this.name = name;
        this.DOB = DOB;
        this.height = height;
        this.weight = weight;
        this.sex =sex;
        this.username = username;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
