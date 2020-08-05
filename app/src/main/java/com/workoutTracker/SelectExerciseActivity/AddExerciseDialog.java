package com.workoutTracker.SelectExerciseActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.workoutTracker.ObservableList;
import com.workoutTracker.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AddExerciseDialog extends DialogFragment {
    private SelectExerciseActivity context;
    ObservableList<String> tools;
    ObservableList<String> exercises;

    public AddExerciseDialog(SelectExerciseActivity context){
        this.context = context;
        tools = context.model.getTools(); // list of tools to pick from
        exercises = context.model.getExercises(); // check if user tries to add a exercise already made
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_exercise_popup, null);
        builder.setView(popupView);
        Button button = popupView.findViewById(R.id.addExercise);
        button.setOnClickListener((a)->{
            TextInputEditText exerciseName =  popupView.findViewById(R.id.exerciseName);
            context.onConfirmExercise(popupView);

        });

        ListView toolList = popupView.findViewById(R.id.toolList);
        if(toolList==null){
            Log.i("Exercise","toolList is null");
        }else{
            Log.i("Exercise","toolList is not null");
        }
        ToolListAdapter adapter = new ToolListAdapter(context,tools);
             toolList.setAdapter(adapter);
            tools.getObservable().addObserver((observable, o) -> {
               adapter.notifyDataSetChanged();

            });
        return builder.create();
    }
}