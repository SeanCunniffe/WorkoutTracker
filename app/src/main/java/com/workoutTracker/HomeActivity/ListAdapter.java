package com.workoutTracker.HomeActivity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.workoutTracker.R;
import com.workoutTracker.Set;

import java.io.File;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Set> data;
    private static LayoutInflater inflater = null;

    public ListAdapter(Context context, ArrayList<Set> data) {
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
        //  Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        //  Auto-generated method stub
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //  Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.record_list_entry, null);
        TextView reps = vi.findViewById(R.id.prReps);
        TextView weight = vi.findViewById(R.id.prWeight);
        String repVal = String.valueOf(data.get(position).getReps());
        String weightVal = String.valueOf(data.get(position).getWeight());
        reps.setText(repVal);
        weight.setText(weightVal);

        return vi;
    }
}
