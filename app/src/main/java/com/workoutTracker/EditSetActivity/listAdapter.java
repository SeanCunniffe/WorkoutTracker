package com.workoutTracker.EditSetActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.workoutTracker.R;
import com.workoutTracker.Set;

import java.util.ArrayList;

public class listAdapter extends BaseAdapter {

    Context context;
    ArrayList<Set> data;
    private static LayoutInflater inflater = null;

    public listAdapter(Context context, ArrayList<Set> data) {
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.table_row, null);
        TextView reps = vi.findViewById(R.id.reps);
        TextView weight = vi.findViewById(R.id.weight);
        ConstraintLayout setTab = vi.findViewById(R.id.setTab);
        String repVal = String.valueOf(data.get(position).getReps());
        String weightVal = String.valueOf(data.get(position).getWeight());
        reps.setText(repVal);
        weight.setText(weightVal);
        Button deleteButton = vi.findViewById(R.id.cancelButton);
        View finalVi = vi;
        deleteButton.setOnClickListener(view -> {
            data.remove(data.get(position));
            Animation fadeout = new AlphaAnimation(1.f, 0.f);
            fadeout.setDuration(500);
            finalVi.startAnimation(fadeout);
            finalVi.postDelayed(this::notifyDataSetChanged, 500);

        });
        return vi;
    }
}
