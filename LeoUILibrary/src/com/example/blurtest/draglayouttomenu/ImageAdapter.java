package com.example.blurtest.draglayouttomenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.blurtest.R;
import java.util.ArrayList;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageAdapter extends BaseAdapter {

    private LayoutInflater infalter;
    private ArrayList<String> paths = new ArrayList<String>();

    public ImageAdapter(Context context) {
        infalter = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public String getItem(int position) {
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(ArrayList<String> paths) {
        this.paths.clear();
        this.paths.addAll(paths);
    }

    public void addItem(String url) {
        paths.add(url);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = infalter.inflate(R.layout.draglayout_gridview_item, null);
            holder = new ViewHolder();
            holder.iv_item = (ImageView) convertView.findViewById(R.id.iv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage("file://" + paths.get(position), holder.iv_item);
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_item;
    }

}
