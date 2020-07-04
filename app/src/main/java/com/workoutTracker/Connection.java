package com.workoutTracker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public DatabaseReference getRef(String ref){
        return database.getReference(ref);
    }

    public static FirebaseAuth getAuth(){
        return mAuth;
    }
}
