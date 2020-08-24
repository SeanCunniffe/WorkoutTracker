package com.workoutTracker.HomeActivity;

public class Record {

    private long topSet;
    private long oneRepMax;
    private long totalWeightMoved = 0;
    private String exerciseName;

    public Record(String exerciseName, long topSet, long oneRepMax){
        this.exerciseName = exerciseName;
        this.topSet = topSet;
        this.oneRepMax = oneRepMax;
    }

    public long getTopSet() {
        return topSet;
    }

    public void setTopSet(long topSet) {
        this.topSet = topSet;
    }

    public long getOneRepMax() {
        return oneRepMax;
    }

    public void setOneRepMax(long oneRepMax) {
        this.oneRepMax = oneRepMax;
    }

    public long getTotalWeightMoved() {
        return totalWeightMoved;
    }

    public void setTotalWeightMoved(long totalWeightMoved) {
        this.totalWeightMoved = totalWeightMoved;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Override
    public String toString() {
        return "Record{" +
                "topSet=" + topSet +
                ", oneRepMax=" + oneRepMax +
                ", totalWeightMoved=" + totalWeightMoved +
                ", exerciseName='" + exerciseName + '\'' +
                '}';
    }
}

