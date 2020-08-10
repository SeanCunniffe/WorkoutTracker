package com.workoutTracker.SignInActivity;

import com.google.firebase.database.DatabaseReference;
import com.workoutTracker.Connection;
import com.workoutTracker.ObservableValue;
import com.workoutTracker.User;

public class Model extends Connection{

//    void createUser(String email){
//        DatabaseReference ref =  getUserRef();
//        String[] name = email.split("@");
//        ref.setValue(new User(name[0],null));
//        ref.push();
//    }

    public ObservableValue<Boolean> userLogin(){
        ObservableValue<Boolean> ob = new ObservableValue<>();
        Runnable run = ()-> {
            synchronized (ob){
                try {
                    ob.wait(2000); // wait two seconds in splash screen
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (getAuth().getCurrentUser() != null) {
                ob.setValue(true);
            }else{
                ob.setValue(false);
            }
        };
        execRunnable(run);
        return ob;
    }
}
