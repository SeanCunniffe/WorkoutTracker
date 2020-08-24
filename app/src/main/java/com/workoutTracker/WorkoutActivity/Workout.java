package com.workoutTracker.WorkoutActivity;

public class Workout {
    private String ExName;
    private String date;
    private String user;
    private int[] repAndSets;

    Workout(String ExName, String date, String user, int[] repsAndSets) {
        this.ExName = ExName;
        this.date = date;
        this.user =user;
        this.repAndSets = repsAndSets;
    }



}