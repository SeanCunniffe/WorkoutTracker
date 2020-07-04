package com.workoutTracker.HomeActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.workoutTracker.Connection;

public class Model {
    FirebaseDatabase database = Connection.database;
    public Model(){
    }

    String getEmail(){
        database.getReference();
        return Connection.getAuth().getCurrentUser().getEmail();
    }

}
