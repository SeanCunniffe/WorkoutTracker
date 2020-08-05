package com.workoutTracker.SelectExerciseActivity;

public class InvalidToolException extends Exception{
    public InvalidToolException(String tool) {
        super(tool);
    }
}
