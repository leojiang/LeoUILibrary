package com.zhi.gui.guide.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.zhi.gui.guide.R;
import com.zhi.gui.guide.data.InternshipBrief;
import com.zhi.gui.guide.data.InternshipFull;
import com.zhi.gui.guide.view.CropToCircleTransformation;

import android.app.Service;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InternshipFullAdapter extends BaseAdapter {

    private List<InternshipFull> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private Boolean mIsCompetenceEnough = false;

    public InternshipFullAdapter(Context context, List<InternshipFull> list) {
        mContext = context;
        setContent(list);
        mInflater = ((LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE));
    }

    public void setmIsCompetenceEnough(boolean flag) {
        mIsCompetenceEnough = flag;
    }

    public void setContent(List<InternshipFull> list) {
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
            convertView = mInflater.inflate(R.layout.internship_full_list_item, null);
            viewHolder.headImageUrl = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.companyName = (TextView) convertView.findViewById(R.id.companyName);
            viewHolder.jobTitle = (TextView) convertView.findViewById(R.id.jobTitle);
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);
            viewHolder.baseCompetence = (TextView) convertView.findViewById(R.id.baseCompetence);
            viewHolder.salaryRange = (TextView) convertView.findViewById(R.id.salary);
            viewHolder.apply = (TextView) convertView.findViewById(R.id.apply);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        InternshipFull internship = (InternshipFull) getItem(i);
        viewHolder.companyName.setText(internship.getCompanyName());
        viewHolder.jobTitle.setText(internship.getJobTitle());
        viewHolder.location.setText("地点:" + internship.getLocation());
        viewHolder.baseCompetence.setText("求职力:" + internship.getBaseCompetence());
        viewHolder.salaryRange.setText(internship.getSalaryRange());
        Picasso.with(mContext).load(Uri.parse(internship.getHeadImageUrl())).transform(new CropToCircleTransformation()).into(viewHolder.headImageUrl);
        if (!mIsCompetenceEnough) {
            viewHolder.apply.setVisibility(View.GONE);
        } else {
            if (internship.getIsApplied()) {
                viewHolder.apply.setText("已投");
                viewHolder.apply.setEnabled(false);
                viewHolder.apply.setBackgroundResource(R.drawable.button_bg_gray);
                viewHolder.apply.setClickable(false);
            } else {
                viewHolder.apply.setText("投一发");
                viewHolder.apply.setEnabled(true);
                viewHolder.apply.setBackgroundResource(R.drawable.button_bg_selector_oval_green);
                viewHolder.apply.setClickable(true);
            }
        }
        return convertView;
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
        ImageView headImageUrl;
        TextView companyName;
        TextView jobTitle;
        TextView location;
        TextView baseCompetence;
        TextView salaryRange;
        TextView apply;
    }
}