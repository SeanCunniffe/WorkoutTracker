package com.workoutTracker.EditSetActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import com.workoutTracker.ObservableList;
import com.workoutTracker.R;
import com.workoutTracker.Set;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class EditSetActivity extends AppCompatActivity {
    ObservableList<Set> sets;
    ListView setList;
    Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String exerciseName = getIntent().getStringExtra("exerciseName");
        model = new Model(this);
        sets = model.getSets(exerciseName);
        setContentView(R.layout.activity_edit_set);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setList = findViewById(R.id.setList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ListAdapter listAdapter = new listAdapter(this,sets);
        setList.setAdapter(listAdapter);
        TextView emptyText = new TextView(this);
        setList.setEmptyView(emptyText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
