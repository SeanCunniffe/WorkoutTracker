package com.workoutTracker.SignInActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.workoutTracker.Connection;
import com.workoutTracker.User;

public class Model {
    FirebaseDatabase database = Connection.database;

    void createUser(String email){
        DatabaseReference ref =  database.getReference("Users");
        String[] name = email.split("@");
        ref.setValue(new User(name[0],null));
        ref.push();
    }
}
