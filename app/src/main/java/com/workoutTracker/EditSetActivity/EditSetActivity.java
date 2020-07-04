package com.workoutTracker.EditSetActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import com.workoutTracker.R;
import com.workoutTracker.Set;
import com.workoutTracker.WorkoutActivity.WorkoutActivity;

import java.util.ArrayList;
import java.util.Objects;

public class EditSetActivity extends AppCompatActivity {
    ArrayList<Set> sets;
    ListView setList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sets = WorkoutActivity.sets;
        setContentView(R.layout.activity_edit_set);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setList = findViewById(R.id.setList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setList.setAdapter(new listAdapter(this,sets));
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
