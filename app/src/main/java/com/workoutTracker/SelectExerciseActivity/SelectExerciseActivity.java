package com.workoutTracker.SelectExerciseActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import com.workoutTracker.R;
import com.workoutTracker.WorkoutActivity.WorkoutActivity;

import java.util.ArrayList;
import java.util.Objects;

public class SelectExerciseActivity extends AppCompatActivity {
    Model model;
    ArrayList<String> exercises = new ArrayList<>();
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new Model();
        setContentView(R.layout.select_exercise_activity);
        setSupportActionBar(findViewById(R.id.toolbar2));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView list = findViewById(R.id.exerciseList);
        list.setAdapter(new ListAdapter(this,exercises));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    public void onClickExercise(View view){
        Intent intent = new Intent(SelectExerciseActivity.this, WorkoutActivity.class);
        TextView exerciseName = view.findViewById(R.id.exerciseName);
        intent.putExtra("exerciseName",exerciseName.getText().toString());
        this.startActivity(intent);

    }


    public void onAddExercise(MenuItem item){
        AddExerciseDialog m = new AddExerciseDialog(this,model);
        FragmentManager fragmentManager = getSupportFragmentManager();
        m.show(fragmentManager,"");
    }

}
