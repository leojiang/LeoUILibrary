package com.zhi.gui.guide.adapter;

import java.util.List;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.data.Competence;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CompetenceListAdapter extends BaseAdapter {

    private List<Competence> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public CompetenceListAdapter(Context context, List<Competence> list) {
        mContext = context;
        setContent(list);
        mInflater = ((LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE));
    }

    public void setContent(List<Competence> list) {
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
            convertView = mInflater.inflate(R.layout.competence_list_item, null);
            viewHolder.rank = (TextView) convertView.findViewById(R.id.rank);
            viewHolder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            viewHolder.competence = (TextView) convertView.findViewById(R.id.competence);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Competence competence = (Competence) getItem(i);
        viewHolder.rank.setText("排名:" + String.valueOf(competence.getRank()));
        viewHolder.nickname.setText(competence.getNickname());
        viewHolder.competence.setText(String.valueOf(competence.getCompetence()));

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
        TextView rank;
        TextView nickname;
        TextView competence;
    }
}