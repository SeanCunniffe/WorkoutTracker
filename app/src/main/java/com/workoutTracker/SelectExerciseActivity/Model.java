package com.workoutTracker.SelectExerciseActivity;

import android.util.Log;
import android.view.contentcapture.DataShareRequest;
import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.workoutTracker.Connection;
import com.workoutTracker.ObservableList;

import java.util.*;

public class Model extends Connection {

    public void addNewExercise(String name, ObservableList<String> tools){
        ObservableList<String> toolList = getTools();
        DatabaseReference ref =  getExercisesRef();
        DatabaseReference exRef =  ref.child(name);
        Map<String, Object> toolsMap = new HashMap<>();
        toolsMap.put("tools",tools);
        exRef.updateChildren(toolsMap);
    }

    public ObservableList<String> getTools() {
        ObservableList<String> toolList = new ObservableList<>();
        DatabaseReference ref = getToolsRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot shot:snapshot.getChildren()){
                    toolList.add((String)shot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return toolList;
    }


    public ObservableList<String> getExercises(){
        ObservableList<String> exercises = new ObservableList<>();
        DatabaseReference ref =  getExercisesRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot shot: snapshot.getChildren()){
                    exercises.add(shot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return exercises;
    }

    public void setExerciseListener(ListAdapter adapter){

    }

    public ObservableList<String> getTools(String exerciseName) {
        ObservableList<String> tools = new ObservableList<>();
        DatabaseReference ref = getExercisesRef();
        DatabaseReference exRef = ref.child(exerciseName).child("tools");
        exRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> shot = snapshot.getChildren();

                for(DataSnapshot snapshot1: shot){
                        tools.add((String)snapshot1.getValue());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return tools;
    }
}


