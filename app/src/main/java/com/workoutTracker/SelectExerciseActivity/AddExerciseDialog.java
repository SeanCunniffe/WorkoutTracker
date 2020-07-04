package com.workoutTracker.SelectExerciseActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.workoutTracker.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AddExerciseDialog extends DialogFragment {
    Model model;
    private Context context;

    public AddExerciseDialog(Context context,Model model){
        this.context = context;
        this.model = model;
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
            model.addNewExercise(exerciseName.getText().toString());
        });
        return builder.create();
    }
}