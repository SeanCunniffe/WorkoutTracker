package com.workoutTracker.WorkoutActivity;

import android.content.Context;
import com.workoutTracker.Set;

import java.io.File;

public class Model {
    Context context;
    File file;

    public Model(Context context) {
        this.context = context;
        file = context.getFilesDir();
    }

    public void addSet(Set set){
    }

    public void removeSet(Set set){

    }
}
