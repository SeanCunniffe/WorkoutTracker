package com.workoutTracker.SelectExerciseActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.workoutTracker.R;
import com.workoutTracker.Set;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {


    Context context;
    ArrayList<String> data;
    private static LayoutInflater inflater = null;

    public ListAdapter(Context context, ArrayList<String> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.exercise_entry, null);
        TextView exerciseName = vi.findViewById(R.id.exerciseName);
        exerciseName.setText(data.get(position));
        exerciseName.setSelected(true);
        return vi;
    }
}
