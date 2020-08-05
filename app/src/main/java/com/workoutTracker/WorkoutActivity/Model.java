package com.workoutTracker.WorkoutActivity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.workoutTracker.Connection;
import com.workoutTracker.ObservableList;
import com.workoutTracker.ObservableValue;
import com.workoutTracker.Set;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Model extends Connection {
    Context context;
    ObservableList<Set> sets;


    public Model(Context context) {
        this.context = context;
    }

    public void saveSet(Set set){
        Runnable run = () -> {
            DatabaseReference ref  = getWorkoutsRef();
            ObservableValue<String> username = getUsername();
            ref = ref.child(username.getValue());
            LocalDate date = LocalDate.now();
            String sDate = date.getDayOfMonth() + date.getMonth().toString()+ date.getYear();
            ref = ref.child(sDate);
            ref = ref.child(set.getExercise());
            Map<String, Integer> setMap = new HashMap<>();
            setMap.put("reps", set.getReps());
            setMap.put("weight",set.getWeight());
            ref.push().setValue(setMap);
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
                    Set set = new Set(snapshot.getKey(), (int) ((long) map.get("weight")), (int) ((long) map.get("reps")), exerciseName);
                    sets.add(set);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    HashMap map = (HashMap) snapshot.getValue();
                    Set removeSet =null;
                    for(Set set: sets){
                        if(set.getID().equals(snapshot.getKey()));
                        removeSet = set;
                        break;
                    }
                    sets.remove(removeSet);
                    Log.i("WorkoutActivity","After removing set: "+removeSet+": "+sets);
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
