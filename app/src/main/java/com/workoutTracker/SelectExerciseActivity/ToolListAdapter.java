package com.workoutTracker.SelectExerciseActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import com.workoutTracker.ObservableList;
import com.workoutTracker.R;

public class ToolListAdapter extends BaseAdapter {
    Context context;
    ObservableList<String> tools;
    static LayoutInflater inflater;
    ObservableList<String> checkedList = new ObservableList<>();

    public ToolListAdapter(Context context, ObservableList<String> tools) {
        this.context = context;
        this.tools = tools;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tools.size();
    }

    @Override
    public Object getItem(int i) {
        return tools.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.tool_entry, null);
        CheckBox cb = vi.findViewById(R.id.checkBox);
        cb.setText(tools.get(i));
        cb.setOnCheckedChangeListener((a, b) -> {
            if (cb.isChecked()) {
                checkedList.add(tools.get(i));
            } else {
                checkedList.remove(tools.get(i));
            }
        });
        return vi;
    }


    public ObservableList<String> getSelectedItems() {
        return checkedList;
    }
}
