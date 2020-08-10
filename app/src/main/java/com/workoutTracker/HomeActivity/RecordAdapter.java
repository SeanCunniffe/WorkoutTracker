package com.workoutTracker.HomeActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.workoutTracker.*;

import java.util.HashMap;
import java.util.Observer;

public class RecordAdapter extends BaseAdapter {
    public static final String LOGTAG = "HomeActivity";

    private Context context;
    private ObservableList<Record> data;
    private static LayoutInflater inflater = null;
    private Model model;
    HashMap<Record,String> recordAndRank;

    public RecordAdapter(Context context, ObservableList<Record> data,Model model) {
        super();
        this.model = model;
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        recordAndRank = new HashMap<>();

        data.getObservable().addObserver((a,b)->{
           Record record =  data.get(data.size()-1);
            ObservableValue<String> rank = getOneRepMaxRank(record);
            Observer observer = (observable, o) -> {
                Log.i(LOGTAG, "list size: " + data);
                recordAndRank.put(record,rank.getValue());
                getView(data.indexOf(record),null,null);
                notifyDataSetChanged();
            };
            rank.addObserver(observer);
        });


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
        View vi = convertView;
        Record record = data.get(position);
        Log.i(LOGTAG,"adding exercise in position: "+position+" "+record.getExerciseName());
        String rank =  recordAndRank.get(record);
        if (vi == null) {
            vi = inflater.inflate(R.layout.record_card, null);
            TextView topSetWeight = vi.findViewById(R.id.topSetWeight);
            TextView oneRepMax = vi.findViewById(R.id.oneRepMax);
            TextView totalWeightMoved = vi.findViewById(R.id.totalWeightMoved);
            TextView exerciseName = vi.findViewById(R.id.exerciseName);
            String sTopSet = String.valueOf(record.getTopSet());
            topSetWeight.setText(sTopSet);
            if (record.getOneRepMax() == 0) {
                oneRepMax.setText("--");
            } else {
                oneRepMax.setText(String.valueOf(record.getOneRepMax()));
            }
            exerciseName.setText(record.getExerciseName());
            totalWeightMoved.setText((String.valueOf(record.getTotalWeightMoved())));

        }
        if(rank !=null){
            ConstraintLayout background = vi.findViewById(R.id.backgroundLayout);
            switch (rank) {
                case StrengthRanks.BEGINNER:
                    background.setBackgroundResource(R.drawable.bronze);
                    break;
                case StrengthRanks.NOVICE:
                    background.setBackgroundResource(R.drawable.silver);
                    break;
                case StrengthRanks.INTERMEDIATE:
                    background.setBackgroundResource(R.drawable.gold);
                    break;
                case StrengthRanks.ADVANCED:
                    background.setBackgroundResource(R.drawable.ruby);
                    break;
                case StrengthRanks.ELITE:
                    background.setBackgroundResource(R.drawable.diamond);
                    break;
            }

        }
        return vi;
    }

    private ObservableValue<String> getOneRepMaxRank(Record record) {
        ObservableValue<String> val = new ObservableValue<>();
        User user = model.userProfile().getValue();
       String sex =  user.getSex();

           ObservableValue<StrengthRanks> ranks = model.getStrengthRank(record.getExerciseName(),sex);
           ranks.addObserver((observable1, o1) -> {
               if(ranks.getValue() != null) {
                  val.setValue(ranks.getValue().getStrengthLevel(user.getWeight().intValue(), (int) record.getOneRepMax()));
               }else{
                   Log.i(LOGTAG,"no ranks for exercise"+ record.getExerciseName());
               }
           });
        return val;
    }
}
