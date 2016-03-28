package com.example.blurtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.blurtest.R;
import com.example.blurtest.util.ImageCache;

public class ImageFilterMainActivity extends Activity {
    ListView mListView;
    private String[] mListStr = {"1 冰冻", "2 熔铸", "3 连环画", "4 柔化美白", "5 照亮边缘", "6 羽化效果", "7 高斯模糊", "8 倒影效果",
            "9 底片效果", "10 浮雕效果"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter_main);
        findView();
    }

    private void findView() {
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListStr));
        mListView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ImageFilterMainActivity.this, ImageFilterActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImageCache.clear();
        super.onDestroy();
    }
}