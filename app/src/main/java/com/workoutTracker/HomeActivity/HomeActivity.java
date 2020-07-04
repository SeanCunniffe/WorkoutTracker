package com.workoutTracker.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.firebase.database.DatabaseReference;
import com.workoutTracker.Connection;
import com.workoutTracker.R;
import com.workoutTracker.SelectExerciseActivity.SelectExerciseActivity;
import com.workoutTracker.Set;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    String username;
    int weight = 95;
    LocalDate dob = LocalDate.of(1997,11,2);
    ArrayList<Set> records = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Model model = new Model();
        setContentView(R.layout.home_activity);

        username = model.getEmail();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View vi;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vi = inflater.inflate(R.layout.nav_header,findViewById(R.id.nav_view));
        TextView userNameTextView = vi.findViewById(R.id.userNameTextView);
        TextView userWeight = vi.findViewById(R.id.userWeightTextView);
        TextView userDOB = vi.findViewById(R.id.userAgeTextView);

        userNameTextView.setText(username);
        String weightS = weight+"Kg";
        userWeight.setText(weightS);
        Period period = Period.between(dob, LocalDate.now());
        String age = period.getYears()+" Years old";
        userDOB.setText(age);
        for(int i=1;i<=10;i++) {
            records.add(new Set(i, i*12, i*i, "Bench Press"+i));
        }
    }

    public void onStartExercise(MenuItem item){
        Intent intent = new Intent(HomeActivity.this, SelectExerciseActivity.class);
        HomeActivity.this.startActivity(intent);
    }


}
