package com.workoutTracker.WorkoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.textfield.TextInputEditText;
import com.workoutTracker.EditSetActivity.EditSetActivity;
import com.workoutTracker.R;
import com.workoutTracker.Set;

import java.util.ArrayList;
import java.util.Objects;

public class WorkoutActivity extends AppCompatActivity {
    Model model;
    int weightInc = 5;
    int weightDec = -5;
    int setCount=0;
    String exercise = "Bench Press";
    public static ArrayList<Set> sets = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new Model(this.getApplicationContext());
        String exerciseName = getIntent().getStringExtra("exerciseName");
        TextView name = findViewById(R.id.exerciseName);
        name.setSelected(true);
        name.setText(exerciseName);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setWeightMoved();
        setLastSet();
    }

    /**
     * Determines the last set done in sets list by ID
     */
    private void setLastSet() {
        Set lastSet = new Set(-1,0,0,"");
        for(Set set: sets){
            if(lastSet == null || set.getID()>lastSet.getID()){
                lastSet = set;
            }
        }
        setPrevSet(lastSet);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setWeightMoved();
        setLastSet();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_sets) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * increments the rep textField
     * @param view
     */
    public void onRepIncrement(View view){
        TextInputEditText repInput =  findViewById(R.id.reptTextField);
        Button decReps = findViewById(R.id.decReps);
        int reps=0;
        try{
        reps = Integer.parseInt(Objects.requireNonNull(repInput.getText()).toString());
        }catch(NumberFormatException ignored){
        }
        reps = reps+1;
        String disRep = reps+"";
        repInput.setText(disRep);
        decReps.setEnabled(true);
    }

    /**
     * Decrements the rep textfield
     * @param view
     */
    public void onRepDecrement(View view){
        TextInputEditText repInput =  findViewById(R.id.reptTextField);
        int reps=0;
        try{
        reps = Integer.parseInt(Objects.requireNonNull(repInput.getText()).toString());
        }catch(NumberFormatException ignored){
        }
        Button decReps = findViewById(R.id.decReps);
        reps = reps-1;
        if(reps<0){
            return;
        }else if(reps==0){
            decReps.setEnabled(false);
        }else{
            decReps.setEnabled(true);
        }
        String disRep = reps+"";
        repInput.setText(disRep);
    }

    /**
     * Increments Weight in textfield
     * @param view
     */
    public void onWeightIncrement(View view){
        TextInputEditText weightInput = findViewById(R.id.weightTextField);
        int weight=0;
        try{
        weight = Integer.parseInt(Objects.requireNonNull(weightInput.getText()).toString());
        }catch(NumberFormatException ignored){
        }
        weight = weight+weightInc;
        String disWeight = weight+"";
        weightInput.setText(disWeight);

    }

    /**
     * Decrements weight in textfield
     * @param view
     */
    public void onWeightDecrement(View view){
        TextInputEditText weightInput = findViewById(R.id.weightTextField);
        int weight = 0;
        try {
            weight = Integer.parseInt(Objects.requireNonNull(weightInput.getText()).toString());
        }catch(NumberFormatException ignored){
        }
        weight = weight+weightDec;
        if(weight<=0){
            weight = 0;
        }
        String disWeight = weight+"";
        weightInput.setText(disWeight);

    }

    /**
     * Save set in sets list
     * @param view
     */
    public void onSaveSet(View view){
        TextInputEditText repInput =  findViewById(R.id.reptTextField);
        TextInputEditText weightInput = findViewById(R.id.weightTextField);
        int weight=0;
        int reps=0;
        try{
            weight = Integer.parseInt(Objects.requireNonNull(weightInput.getText()).toString());
            reps = Integer.parseInt(Objects.requireNonNull(repInput.getText()).toString());
            if(weight <=0 || reps <= 0){
                throw new Exception();
            }
        }catch(Exception e){
            Toast t = Toast.makeText(getApplicationContext(),"Invalid fields",Toast.LENGTH_LONG);
            t.show();
            return;
        }
        Set set = new Set(setCount++,weight,reps,exercise);
        sets.add(set);
        setPrevSet(set);
        setWeightMoved();
        Toast t = Toast.makeText(getApplicationContext(),"Set of "+weight+"Kg"+" for "+reps+" rep(s) saved",Toast.LENGTH_LONG);
        t.show();
    }

    private void setWeightMoved() {
        int weightMoved=0;
        for(Set set:sets){
            weightMoved = weightMoved+(set.getReps()*set.getWeight());
        }
        TextView weightMovedDis = findViewById(R.id.totalWeightMoved);
        String weightMovedVal = weightMoved+"";
        weightMovedDis.setText(weightMovedVal);
    }

    /**
     * Displays last set done
     * @param set
     */
    private void setPrevSet(Set set) {
        TextView weight = findViewById(R.id.lastSetWeight);
        TextView reps = findViewById(R.id.lastSetReps);
        String weightVal = set.getWeight()+"";
        String repVal = set.getReps()+"";
        weight.setText(weightVal);
        reps.setText(repVal);
    }

    public void onEditSet(MenuItem menuItem){
        editSetActivity();
    }

    private void editSetActivity(){
        Intent intent = new Intent(WorkoutActivity.this, EditSetActivity.class);
        WorkoutActivity.this.startActivity(intent);
    }
}
