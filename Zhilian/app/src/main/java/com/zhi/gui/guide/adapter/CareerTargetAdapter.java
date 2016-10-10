package com.zhi.gui.guide.adapter;

import java.util.List;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.data.CareerTarget;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CareerTargetAdapter extends BaseAdapter {

    private List<CareerTarget> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public CareerTargetAdapter(Context context, List<CareerTarget> list) {
        mContext = context;
        setContent(list);
        mInflater = ((LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE));
    }

    public void setContent(List<CareerTarget> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.career_target_list_item, null);
            viewHolder.index = (TextView) convertView.findViewById(R.id.index);
            viewHolder.industry = (TextView) convertView.findViewById(R.id.industry);
            viewHolder.percentage = (TextView) convertView.findViewById(R.id.percentage);
            viewHolder.level = (TextView) convertView.findViewById(R.id.career_level);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CareerTarget careerTarget = (CareerTarget) getItem(i);
        viewHolder.index.setText(String.valueOf(careerTarget.getIndex()));
        viewHolder.industry.setText(careerTarget.getIndustry());
        viewHolder.percentage.setText(String.valueOf(careerTarget.getPercentage()));
        setLevel(viewHolder.level, careerTarget.getCareerLevel());

        return convertView;
    }

    private void setLevel(TextView view, int level) {
        switch (level) {
            case 0:
                view.setBackgroundColor(0);
                view.setText(null);
                break;
            case 1:
                view.setBackgroundColor(Color.parseColor("#7fc369"));
                view.setText("主要方向");
                break;
            case 2:
                view.setBackgroundColor(Color.parseColor("#fd909e"));
                view.setText("次要方向");
                break;
            default:
                throw new RuntimeException("career level not supported");
        }
    }

    @Override
    public Object getItem(int i) {
        return mList == null ? 0 : mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView index;
        TextView industry;
        TextView percentage;
        TextView level;
    }
}