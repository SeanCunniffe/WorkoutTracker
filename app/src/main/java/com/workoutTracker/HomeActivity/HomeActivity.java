package com.workoutTracker.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.workoutTracker.*;
import com.workoutTracker.SelectExerciseActivity.SelectExerciseActivity;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    String username;
    ArrayList<Set> records = new ArrayList<>();
    DrawerLayout drawerLayout;
    Model model;
    ObservableList<Record> recordList;
    ListView recordListView;
    private static final String logTag = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new Model();
        setContentView(R.layout.home_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View vi;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vi = inflater.inflate(R.layout.nav_header,findViewById(R.id.nav_view));
        TextView userWeight = vi.findViewById(R.id.userWeightTextView);
        TextView userDOB = vi.findViewById(R.id.userAgeTextView);
        model.userProfile().addObserver((observable, o) -> { // get user profile first
            User user = model.userProfile().getValue();
            String username = user.getUsername();
            TextView userNameTextView = vi.findViewById(R.id.userNameTextViewHeader);
            //TODO username coming in but not displaying
            long weight = user.getWeight();
            String weightS = weight+"Kg";
            userWeight.setText(weightS);
            String sDOB = user.getDOB();
            String[] aDOB = sDOB.split("/");
            LocalDate dob = LocalDate.of(Integer.parseInt(aDOB[2]),Month.of(Integer.parseInt(aDOB[1])),Integer.parseInt(aDOB[0]));
            Period period = Period.between(dob, LocalDate.now());
            String age = period.getYears()+" Years old";
            userDOB.setText(age);
            userNameTextView.setText(username);
            recordList = model.getRecordList();
            setupRecord(recordList);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setupRecord(ObservableList<Record> recordList) {
        this.recordList = recordList;
        recordListView = findViewById(R.id.recordList);
        RecordAdapter adapter = new RecordAdapter(this,recordList,model); // TODO create adapter for record list
        recordList.getObservable().addObserver((observable, o) -> {
            adapter.notifyDataSetChanged();
        });
        recordListView.setAdapter(adapter);

    }

    public void onStartExercise(MenuItem item){
        Intent intent = new Intent(HomeActivity.this, SelectExerciseActivity.class);
        HomeActivity.this.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawerLayout.close();

    }
}
