package com.workoutTracker;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class Connection {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static ObservableValue<String> username;
    private static final ExecutorService exec = Executors.newFixedThreadPool(10);
    private static ObservableValue<User> user;

    public DatabaseReference getUserRef(){
        String ref = "Users";
        return database.getReference(ref);
    }

    public DatabaseReference getExercisesRef(){
        String ref = "Exercises";
        return database.getReference(ref);
    }

    public DatabaseReference getToolsRef(){
        String ref = "Tools";
        return database.getReference(ref);
    }

    public DatabaseReference getWorkoutsRef(){
        String ref = "Workouts";
        return database.getReference(ref);
    }

    public String getEmail(){
        return getAuth().getCurrentUser().getEmail();
    }

    public ObservableValue<User> userProfile(){ //TODO not finished
        Runnable run = ()-> {
            if (user == null) {
                user = new ObservableValue<>();
                DatabaseReference ref = getUserRef();
                ref = ref.child(username.getValue());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap profile = new HashMap();
                        String DOB = (String) profile.get("DOB");
                        long height = (long) profile.get("height");
                        String name = (String) profile.get("name");
                        String sex = (String) profile.get("sex");
                        long weight = (long) profile.get("weight");
                        User user1 = new User(name, DOB, height, weight, sex);
                        user.setValue(user1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };
        execRunnable(run);
        return user;
    }

    public static FirebaseAuth getAuth(){
        return mAuth;
    }

    public void execRunnable(Runnable runnable){
        exec.execute(runnable);
    }

    public Future execRunnable(Callable callable){
        Future f = exec.submit(callable);
        return f;
    }
}
