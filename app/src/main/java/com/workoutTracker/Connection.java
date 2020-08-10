package com.workoutTracker;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
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
        if(user == null) {
            user = new ObservableValue<>();
            Runnable run = () -> {
                DatabaseReference ref = getUserRef();
                ref = ref.child(username.getValue());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap profile = (HashMap) snapshot.getValue();
                        String DOB = (String) profile.get("dob");
                        Double height = (Double) profile.get("height"); //TODO convert errors
                        String name = (String) profile.get("name");
                        String sex = (String) profile.get("sex");
                        Long weight = (Long) profile.get("weight"); //TODO convert errors
                        String username = Connection.username.getValue();
                        User user1 = new User(name, DOB, height, weight, sex, username);
                        user.setValue(user1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            };
            username = getUsername();
            username.addObserver((observable, o) -> execRunnable(run)); // when username comes in
        }
        return user;
    }

    protected ObservableValue<String> getUsername(){
        if (username == null) {
            username = new ObservableValue<>();
            DatabaseReference ref = getUserRef();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshotUser : snapshot.getChildren()) {
                        String username1 = snapshotUser.getKey();
                        HashMap map = (HashMap) snapshotUser.getValue();
                        String email = (String) map.get("email");
                        String email2 = Objects.requireNonNull(getAuth().getCurrentUser()).getEmail();
                        if (email.equals(email2)) {
                            username.setValue(username1);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        return username;
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
