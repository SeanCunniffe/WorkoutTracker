package com.workoutTracker.SelectExerciseActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.workoutTracker.R;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {


    Context context;
    ArrayList<String> data;
    private static LayoutInflater inflater = null;

    public ListAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.exercise_entry, null);
        TextView exerciseName = vi.findViewById(R.id.exerciseName);
        exerciseName.setText(data.get(position));
        exerciseName.setSelected(true);
        return vi;
    }

    public void addItem(String name){
        View view = inflater.inflate(R.layout.exercise_entry,null);
        TextView exerciseName = view.findViewById(R.id.exerciseName);
        exerciseName.setText(name);
        exerciseName.setSelected(true);


    }
}
