package com.workoutTracker.HomeActivity;

import androidx.annotation.NonNull;
import com.google.firebase.database.*;
import com.workoutTracker.*;

import java.util.HashMap;

public class Model extends Connection{
    private static final String logTag = "HomeActivity";
    ObservableList<Record> records;
    public Model(){

    }


    public ObservableList<Record> getRecordList() {
            records = new ObservableList<>();
            DatabaseReference ref = getWorkoutsRef();
            ref = ref.child(getUsername().getValue());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     for(DataSnapshot snapshot1: snapshot.getChildren()){//dates
                         exercises:
                         for(DataSnapshot snapshot2: snapshot1.getChildren()){ //exercises
                             Record record = null;
                             boolean addSet=false;
                             if(!records.isEmpty()) { // going to be empty for first record
                                 for(Record rec:records){
                                     if(rec.getExerciseName().equals(snapshot2.getKey())){
                                         record = rec;
                                         break;
                                     }
                                 }
                                 if(record == null){
                                     // went through the records and couldn't find a record with
                                     // this exercise name
                                     record = new Record(snapshot2.getKey(),0,0);
                                     addSet =true;
                                 }
                                 sets:
                                 for (DataSnapshot snapshot3 : snapshot2.getChildren()) { // go through all sets of this exercise
                                        HashMap set = (HashMap) snapshot3.getValue();
                                        compareSetToRecord(record, set);
                                 }
                                 if(addSet)records.add(record);
                             }else{
                                 record = new Record(snapshot2.getKey(),0,0);
                                 for (DataSnapshot snapshot3 : snapshot2.getChildren()) { // go through all sets of this exercise
                                     HashMap set = (HashMap) snapshot3.getValue();
                                     compareSetToRecord(record,set);
                                     records.add(record);
                                 }
                             }

                         }
                     }
                }
                private Record compareSetToRecord(Record record, HashMap set){
                    long reps = (long) set.get("reps");
                    long weight = (long) set.get("weight");
                    long weightMoved = reps*weight;
                    if(reps == 1 && weight>record.getOneRepMax())record.setOneRepMax(weight);
                    if(weightMoved>record.getTopSet()) record.setTopSet(weightMoved);
                    record.setTotalWeightMoved(record.getTotalWeightMoved()+weightMoved);
                    return record;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        return records;

    }

    public ObservableValue<StrengthRanks> getStrengthRank(String exerciseName, String sex) {
        for(StrengthRanks ranks: StrengthRanks.savedStrengthRanks){ // if we have it already dont load it from the database
            if(ranks.getExercise().equals("exerciseName") && ranks.getSex().equals(sex)){
                return new ObservableValue<>(ranks);
            }
        }
        ObservableValue<StrengthRanks> ranks = new ObservableValue<>();
        DatabaseReference ref = getExercisesRef();
        ref = ref.child(exerciseName);
        ref = ref.child("strengthRank");
        ref.child(sex);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    HashMap map = (HashMap) snapshot.getValue();
                    String file = (String) map.get(sex);
                    StrengthRanks rank = new StrengthRanks(exerciseName, sex, file);
                    ranks.setValue(rank);
                    if (!StrengthRanks.savedStrengthRanks.contains(rank)) StrengthRanks.savedStrengthRanks.add(rank);
                }catch(NullPointerException e){
                    //NO STRENGTHRANK
                    ranks.notifyObservers(); // observers check if value is null
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ranks;
    }
}
