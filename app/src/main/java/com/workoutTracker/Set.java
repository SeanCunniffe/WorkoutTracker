package com.workoutTracker;

import java.io.Serializable;

public class Set implements Serializable {
    private String ID;
    private int weight;
    private int reps;
    private String exercise;

    public Set(String ID,int weight,int reps,String exercise){
        this.ID = ID;
        this.weight=weight;
        this.reps=reps;
        this.exercise=exercise;
    }

    public String getID() {
        return ID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    @Override
    public String toString() {
        return "Set{" +
                "ID='" + ID + '\'' +
                ", weight=" + weight +
                ", reps=" + reps +
                ", exercise='" + exercise + '\'' +
                '}';
    }
}
