package com.workoutTracker.HomeActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.workoutTracker.ObservableList;
import com.workoutTracker.ObservableValue;
import com.workoutTracker.R;
import com.workoutTracker.StrengthRanks;

import java.util.Observable;
import java.util.Observer;

public class RecordAdapter extends BaseAdapter {
    public static final int BRONZE = 0;
    public static final int SILVER = 1;
    public static final int GOLD = 2;
    public static final int RUBY = 3;
    public static final int DIAMOND = 4;

    private Context context;
    private ObservableList<Record> data;
    private static LayoutInflater inflater = null;
    private Model model;

    public RecordAdapter(Context context, ObservableList<Record> data,Model model) {
        super();
        this.model = model;
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
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //  Auto-generated method stub
        Record record = data.get(position);
        View vi = convertView;
        if (vi == null) vi = inflater.inflate(R.layout.record_card, null);
        ConstraintLayout background = vi.findViewById(R.id.backgroundLayout);
        TextView topSetWeight = vi.findViewById(R.id.topSetWeight);
        TextView oneRepMax = vi.findViewById(R.id.oneRepMax);
        TextView totalWeightMoved = vi.findViewById(R.id.totalWeightMoved);
        TextView exerciseName = vi.findViewById(R.id.exerciseName);
        setBackground(record);
        String sTopSet = String.valueOf(record.getTopSet());
        topSetWeight.setText(sTopSet);
        if(record.getOneRepMax() == 0){
            oneRepMax.setText("--");
        }else {
            oneRepMax.setText(String.valueOf(record.getOneRepMax()));
        }
        exerciseName.setText(record.getExerciseName());
        totalWeightMoved.setText((String.valueOf(record.getTotalWeightMoved())));



        return vi;
    }

    private void setBackground(Record record) {
       ObservableValue<String> sex =  model.getUserSex();

       sex.addObserver((observable, o) -> {
           ObservableValue<StrengthRanks> ranks = model.getStrengthRank(record.getExerciseName(),sex.getValue());
           ranks.addObserver((observable1, o1) -> {
               ranks.getValue().getStrengthLevel(getWeight(),(int)record.getOneRepMax());
           });
       });

    }
}
