package com.workoutTracker.SignInActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.workoutTracker.Connection;
import com.workoutTracker.HomeActivity.HomeActivity;
import com.workoutTracker.R;


public class SignInActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    TextInputEditText email;
    EditText password;
    Button login;
    Button register;
    Model model;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new Model();
        setContentView(R.layout.signin_activity);
        mAuth = Connection.getAuth();
        initVar();
        if (mAuth.getCurrentUser() != null) {
            navHome(mAuth.getCurrentUser().getEmail());
        }
    }

    private void initVar() {
        email = findViewById(R.id.emailField);
        password =findViewById(R.id.passwordField);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.regButton);
    }

    public void onLogin(View view){
        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener((task)->{
            Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG);
            navHome(email.getText().toString());
        });

    }

    private void navHome(String email) {
        Intent intent = new Intent(SignInActivity.this,HomeActivity.class);
        intent.putExtra("Email",email);
        startActivity(intent);
    }

    public void onCreateUser(View view){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage("Would you like to create an account with the credentials entered?");
        build.setPositiveButton("Create", (dialog, id) -> createUser());
        build.setNegativeButton("Cancel",(dialog,id)->{
        });
        build.show();
    }

    private void createUser() {
        String emailS = email.getText().toString();
        Task t = mAuth.createUserWithEmailAndPassword(emailS,password.getText().toString());
        model.createUser(emailS);
        t.addOnCompleteListener((Task)->{
            Toast.makeText(this,"Account Created",Toast.LENGTH_LONG);
            navHome(emailS);
        });
        t.addOnFailureListener((task)->{
            Toast.makeText(this,"Unsuccessful creating account",Toast.LENGTH_LONG);
        });
    }
}

