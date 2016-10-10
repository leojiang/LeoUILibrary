package com.zhi.gui.guide.adapter;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zhi.gui.guide.R;
import com.zhi.gui.guide.view.CropToCircleTransformation;

import java.util.List;

public class ImageListAdapter extends BaseAdapter {
    private List<String> imageUrlList;
    private Context mContext;
    private LayoutInflater mInflater;

    public ImageListAdapter(Context context, List<String> list) {
        mContext = context;
        imageUrlList = list;
        mInflater = ((LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public int getCount() {
        return imageUrlList.size();
    }

    @Override
    public Object getItem(int position) {
        if (imageUrlList != null) {
            return imageUrlList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.image_list_item, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview);
        Picasso.with(mContext).load(imageUrlList.get(position)).into(imageView);

        return convertView;
    }
}
