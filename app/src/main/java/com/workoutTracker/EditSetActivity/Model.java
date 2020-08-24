package com.workoutTracker.EditSetActivity;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.*;
import com.workoutTracker.Connection;
import com.workoutTracker.ObservableList;
import com.workoutTracker.ObservableValue;
import com.workoutTracker.Set;
import com.workoutTracker.WorkoutActivity.WorkoutActivity;

import java.time.LocalDate;
import java.util.HashMap;

public class Model extends Connection {
    private Context context;
    ObservableList<Set> sets;
    public Model(Context context){
        this.context = context;
    }

    public void removeSet(Set set){
        Runnable run = ()->{
            DatabaseReference ref = getWorkoutsRef();
            ObservableValue<String> username = getUsername();
            ref = ref.child(username.getValue());
            LocalDate date = LocalDate.now();
            String sDate = date.getDayOfMonth() + date.getMonth().toString()+ date.getYear();
            ref = ref.child(sDate);
            ref = ref.child(set.getExercise());
            ref = ref.child(set.getID());
            ref.setValue(null);
        };
        execRunnable(run);
    }

    public ObservableList<Set> getSets(String exerciseName){
        if(sets == null) {
            sets = new ObservableList<>();
            DatabaseReference ref = getWorkoutsRef();
            ref = ref.child(getUsername().getValue());
            LocalDate date = LocalDate.now();
            String sDate = date.getDayOfMonth() + date.getMonth().toString() + date.getYear();
            ref = ref.child(sDate);
            ref = ref.child(exerciseName);
            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    HashMap map = (HashMap) snapshot.getValue();
                    Set set = new Set(snapshot.getKey(), (int) (long) map.get("weight"), (int) (long) map.get("reps"), exerciseName);
                    sets.add(set);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    HashMap map = (HashMap) snapshot.getValue();
                    Set set = new Set(snapshot.getKey(), (int) (long) map.get("weight"), (int) (long) map.get("reps"), exerciseName);
                    sets.remove(set);
                    Log.i("EditSetActivity","After removing set: "+set+": "+sets);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return sets;
    }
}
