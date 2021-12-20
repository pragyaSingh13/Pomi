package com.example.pomodoro;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GoalItemAdapter extends BaseAdapter {

    Context context;

    protected List<GoalText> goalList;
    LayoutInflater inflater;

    public GoalItemAdapter(Context _context, List<GoalText> _goalList){
        this.context = _context;
        this.goalList= _goalList;
        this.inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return goalList.size();
    }

    @Override
    public Object getItem(int position) {
        return goalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.goal_list_item,
                    parent, false);

            holder.txtDesc = (TextView) convertView
                    .findViewById(R.id.desc_text);
            holder.txtDate = (TextView) convertView
                    .findViewById(R.id.date_text);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GoalText goalText = goalList.get(position);
        holder.txtDesc.setText(goalText.getDesc());
        holder.txtDate.setText(""+goalText.getDate()); //using ("" +) instead of .toString()

        return convertView;
    }
    private class ViewHolder {
        TextView txtDesc;
        TextView txtDate;
    }
}


