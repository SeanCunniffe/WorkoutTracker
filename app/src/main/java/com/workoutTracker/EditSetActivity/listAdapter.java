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
import com.workoutTracker.ObservableList;
import com.workoutTracker.R;
import com.workoutTracker.Set;

import java.util.ArrayList;

public class listAdapter extends BaseAdapter {

    Context context;
    ObservableList<Set> data;
    private static LayoutInflater inflater = null;
    Model model;

    public listAdapter(Context context, ObservableList<Set> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        model = new Model(context);
        //when new set added it will update the adapter
        data.getObservable().addObserver((observable, o) -> notifyDataSetChanged());
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
            Set removeSet = data.get(position);
            data.remove(removeSet);
            model.removeSet(removeSet);
            Animation fadeout = new AlphaAnimation(1.f, 0.f);
            fadeout.setDuration(500);
            finalVi.startAnimation(fadeout);
            finalVi.postDelayed(this::notifyDataSetChanged, 500);

        });
        return vi;
    }
}
