package com.workoutTracker.WorkoutActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.workoutTracker.R;

public class TimeAndDistanceFragment extends Fragment {
    private String exercise;
    private Context context;
    View root;

    public TimeAndDistanceFragment(String exercise, Context context){
        super();
        this.exercise = exercise;
        this.context = context;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.time_and_distance,container,false);
        TextView name = root.findViewById(R.id.exerciseName);
        name.setSelected(true);
        name.setText(exercise);
        return root;
    }
}
