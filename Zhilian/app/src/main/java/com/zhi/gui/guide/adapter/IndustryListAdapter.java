package com.zhi.gui.guide.adapter;

import java.util.List;

import com.zhi.gui.guide.R;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IndustryListAdapter extends BaseAdapter {

    private List<String> mIndustryList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int selectedItem = 0;

    public IndustryListAdapter(Context context, List<String> list) {
        mContext = context;
        setContent(list);
        mInflater = ((LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE));
    }

    public void setSelectedItem(int index) {
        selectedItem = index;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    @Override
    public int getCount() {
        if (mIndustryList == null) {
            return 0;
        }
        return mIndustryList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.industry_list_item, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText((CharSequence) getItem(position));
        if(position == selectedItem) {
            textView.setBackgroundColor(Color.parseColor("#00b173"));
        } else {
            textView.setBackgroundColor(0);
        }
        return convertView;
    }

    @Override
    public Object getItem(int i) {
        return mIndustryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setContent(List<String> content) {
        mIndustryList = content;
    }
}