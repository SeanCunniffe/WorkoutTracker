package com.workoutTracker.WorkoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.workoutTracker.EditSetActivity.EditSetActivity;
import com.workoutTracker.ObservableList;
import com.workoutTracker.R;
import com.workoutTracker.SelectExerciseActivity.SelectExerciseActivity;
import com.workoutTracker.Set;

import java.util.*;

public class WorkoutActivity extends AppCompatActivity {
    Model model;
    int weightInc = 5;
    int weightDec = -5;
    double timeInc = 1;
    double timeDec = -1;
    double distanceInc = 0.5;
    double distanceDec = -0.5;
    int setCount = 0;
    public static ObservableList<Set> sets;
    RepCounterFragment repCounter;
    TimeAndDistanceFragment timeAndDistanceFragment;
    String exercise;
    ObservableList<String> tools;
    TabAdapter adapter;
    private final CharSequence repCounterName = "rep_counter";
    private final CharSequence distanceName = "Distance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new Model(this.getApplicationContext());
        exercise = getIntent().getStringExtra("exerciseName");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //sets last set done of that day went set removed and added
        setupFragments();
        sets = model.getSets(exercise);
        sets.getObservable().addObserver((observable, o) -> {
            setLastSet();
            setWeightMoved();
        });
    }

    private void setupFragments() {
        tools = SelectExerciseActivity.exerciseToolList;
        ViewPager pager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        if(!tools.isEmpty()){
            for(String tool: tools) {
                addFragment(tool);
            }
        }else{
            Log.i("WorkoutActivity","Tool list is empty");
        }
        tools.getObservable().addObserver((observable, o) -> {
                String tool = tools.get(tools.size()-1);
                addFragment(tool);
        });

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    /**
     * Add fragment to pager
     * @param tool
     */
    private void addFragment(String tool) {
        if(tool.contains(repCounterName)){
            repCounter = new RepCounterFragment(exercise, this);
            adapter.addFragment(repCounter, "Rep Counter");
            adapter.notifyDataSetChanged();
        }
        if(tool.contains(distanceName)){
            timeAndDistanceFragment = new TimeAndDistanceFragment(exercise,this);
            adapter.addFragment(timeAndDistanceFragment,"Time and Distance");
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!sets.isEmpty()) {
            Log.i("WorkoutActivity", sets.toString());
            sets.getObservable().notifyObservers();
        }
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

    public void onEditSet(MenuItem menuItem){
        editSetActivity();
    }

    private void editSetActivity(){
        Intent intent = new Intent(this, EditSetActivity.class);
        intent.putExtra("exerciseName",exercise);
        this.startActivity(intent);
    }


    /**
     * Determines the last set done in sets list by ID
     */
    private void setLastSet() {
        sets.sort(Comparator.comparing(Set::getID));
        try {
            setPrevSet(sets.get(sets.size() - 1));
        }catch(IndexOutOfBoundsException e){
            setPrevSet(null);
        }
    }

    /**
     * Displays last set done
     * @param set
     */
    private void setPrevSet(Set set) {
        TextView weight = findViewById(R.id.lastSetWeight);
        TextView reps = findViewById(R.id.lastSetReps);
        if(set == null){
            weight.setText("--");
            reps.setText("--");
        }else {
            if(weight !=null && reps !=null) {
                String weightVal = set.getWeight() + "";
                String repVal = set.getReps() + "";
                weight.setText(weightVal);
                reps.setText(repVal);
            }
        }
    }

    private void setWeightMoved() {
        if(repCounter == null) return;
        int weightMoved=0;
        for(Set set:sets){
            Log.i("WorkoutActivity",weightMoved+"+"+set.getReps()*set.getWeight());
            weightMoved = weightMoved+(set.getReps()*set.getWeight());
        }
        TextView weightMovedDis = repCounter.getView().findViewById(R.id.totalWeightMoved);
        String weightMovedVal = weightMoved+"";
        weightMovedDis.setText(weightMovedVal);
    }

    /**
     * Save set in sets list
     * @param view
     */
    public void onSaveSet(View view){
        TextInputEditText repInput =  repCounter.getView().findViewById(R.id.reptTextField);
        TextInputEditText weightInput = repCounter.getView().findViewById(R.id.weightTextField);
        int weight=0;
        int reps=0;
        try{
            weight = Integer.parseInt(Objects.requireNonNull(weightInput.getText()).toString());
            reps = Integer.parseInt(Objects.requireNonNull(repInput.getText()).toString());
            if(weight <=0 || reps <= 0){
                throw new Exception();
            }
        }catch(Exception e){
            Toast t = Toast.makeText(this,"Invalid fields",Toast.LENGTH_LONG);
            t.show();
            return;
        }
        Set set = new Set("",weight,reps,exercise);
        model.saveSet(set);
        Toast t = Toast.makeText(this,"Set of "+weight+"Kg"+" for "+reps+" rep(s) saved",Toast.LENGTH_LONG);
        t.show();
    }

    /**
     * increments the rep textField
     * @param view
     */
    public void onRepIncrement(View view){
        TextInputEditText repInput =  repCounter.getView().findViewById(R.id.reptTextField);
        Button decReps = repCounter.getView().findViewById(R.id.decReps);
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
        TextInputEditText repInput =  repCounter.getView().findViewById(R.id.reptTextField);
        int reps=0;
        try{
            reps = Integer.parseInt(Objects.requireNonNull(repInput.getText()).toString());
        }catch(NumberFormatException ignored){
        }
        Button decReps = repCounter.getView().findViewById(R.id.decReps);
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
        TextInputEditText weightInput = repCounter.getView().findViewById(R.id.weightTextField);
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
        TextInputEditText weightInput = repCounter.getView().findViewById(R.id.weightTextField);
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
    public void onTimeIncrement(View view){
        TextInputEditText timeTextField = timeAndDistanceFragment.getView().findViewById(R.id.timeTextField);
        String sTime = timeTextField.getText().toString();
        double dTime = 0;
        try {
            dTime = Double.parseDouble(sTime);
        }catch(Exception ignored){
        }
            dTime = dTime + timeInc;
            sTime = dTime + "";
            timeTextField.setText(sTime);

    }

    public void onTimeDecrement(View view){
        TextInputEditText timeTextField = timeAndDistanceFragment.getView().findViewById(R.id.timeTextField);
        String sTime = timeTextField.getText().toString();
        double dTime=0;
        try {
            dTime = Double.parseDouble(sTime);
        }catch(Exception ignored){

        }
        if((dTime+timeDec)<0){
            dTime = 0;
        }else {
            dTime = dTime + timeDec;
        }
        sTime = dTime + "";
        timeTextField.setText(sTime);
    }

    public void onDistanceIncrement(View view){
        TextInputEditText distanceTextField = timeAndDistanceFragment.getView().findViewById(R.id.distanceTextField);
        String sDistance = distanceTextField.getText().toString();
        double dDistance=0;
        try {
            dDistance = Double.parseDouble(sDistance);
        }catch(Exception ignored){
        }
            dDistance = dDistance + distanceInc;
            sDistance = dDistance + "";
            distanceTextField.setText(sDistance);
    }

    public void onDistanceDecrement(View view){
        TextInputEditText distanceTextField = timeAndDistanceFragment.getView().findViewById(R.id.distanceTextField);
        String sDistance = distanceTextField.getText().toString();
        double dDistance=0;
        try {
            dDistance = Double.parseDouble(sDistance);
        }catch(Exception ignored){
        }
        if((dDistance+distanceDec)<0){
            dDistance = 0;
        }else {
            dDistance = dDistance + distanceDec;
        }
            sDistance = dDistance + "";
            distanceTextField.setText(sDistance);
    }
}


