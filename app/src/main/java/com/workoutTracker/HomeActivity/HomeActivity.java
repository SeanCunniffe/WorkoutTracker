package com.workoutTracker.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.firebase.database.DatabaseReference;
import com.workoutTracker.*;
import com.workoutTracker.SelectExerciseActivity.SelectExerciseActivity;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class HomeActivity extends AppCompatActivity {

    String username;
    int weight = 95;
    LocalDate dob = LocalDate.of(1997,11,2);
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
        ObservableValue<String> username = model.getUsername();
        TextView userNameTextView = vi.findViewById(R.id.userNameTextView);
        username.addObserver((observable, o) -> {
            userNameTextView.setText(username.getValue());
            recordList = model.getRecordList();
            setupRecord(recordList); // wait till we have the username for the model
        });

        String weightS = weight+"Kg";
        userWeight.setText(weightS);
        Period period = Period.between(dob, LocalDate.now());
        String age = period.getYears()+" Years old";
        userDOB.setText(age);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recordList !=null){
            setupRecord(model.getRecordList());
        }

    }

    private void setupRecord(ObservableList<Record> recordList) {
        this.recordList = recordList;
        recordListView = findViewById(R.id.recordList);
        RecordAdapter adapter = new RecordAdapter(this,recordList,model); // TODO create adapter for record list
        recordList.getObservable().addObserver((observable, o) -> {
            Log.i(logTag,"records list has been updated");
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
