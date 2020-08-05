package com.workoutTracker.SelectExerciseActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.textfield.TextInputEditText;
import com.workoutTracker.R;
import com.workoutTracker.WorkoutActivity.WorkoutActivity;
import  com.workoutTracker.ObservableList;

import java.util.Objects;


public class SelectExerciseActivity extends AppCompatActivity {
    /**
     * TODO add textview to exercise name that will display whether the user has done the exercise today, sort to top of list
     */
    Model model;
    ListView list;
    AddExerciseDialog addExerciseDialog;
    public static ObservableList<String> exerciseToolList; // is static because we cant pass the observables through parcel in intent

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new Model();
        setContentView(R.layout.select_exercise_activity);
        setSupportActionBar(findViewById(R.id.toolbar2));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list = findViewById(R.id.exerciseList);

        ObservableList<String> exercises = model.getExercises();

        ListAdapter listAdapter = new ListAdapter(this,exercises);
        model.setExerciseListener(listAdapter);
        list.setAdapter(listAdapter);
        exercises.getObservable().addObserver((observable, o) -> {
            listAdapter.notifyDataSetChanged();
        });
        listAdapter.notifyDataSetChanged();


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
        exerciseToolList = model.getTools(exerciseName.getText().toString());
        this.startActivity(intent);

    }


    public void onAddExercise(MenuItem item){
        addExerciseDialog = new AddExerciseDialog(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        addExerciseDialog.show(fragmentManager,"");
    }

    public void onConfirmExercise(View view){ // user clicks add in add_exercise_popup
        TextInputEditText name =  view.findViewById(R.id.exerciseName);
        String mName = name.getText().toString();
        ListView list = view.findViewById(R.id.toolList);
        ToolListAdapter tla = (ToolListAdapter) list.getAdapter();
        model.addNewExercise(mName,tla.getSelectedItems());
        addExerciseDialog.dismiss();
    }

    public void onCancelNewExercise(View view){
        addExerciseDialog.dismiss();
    }
}
