package com.example.blurtest.draglayouttomenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blurtest.R;
import com.example.blurtest.view.DragLayout;
import com.example.blurtest.view.DragLayout.DragListener;
import com.nineoldandroids.view.ViewHelper;

public class DragLayoutActivity extends Activity {

    private DragLayout mDragLayout;
    private GridView mGridView;
    private ImageAdapter adapter;
    private ListView settingListView;
    private TextView textNothing;
    private ImageView titleIcon, settingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_draglayout);
        Util.initImageLoader(this);
        initDragLayout();
        initView();
    }

    private void initDragLayout() {
        mDragLayout = (DragLayout) findViewById(R.id.dl);
        mDragLayout.setDragListener(new DragListener() {
            @Override
            public void onOpen() {
//                settingListView.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
                shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(titleIcon, 1 - percent);
            }
        });
    }

    private void initView() {
        titleIcon = (ImageView) findViewById(R.id.iv_icon);
        settingIcon = (ImageView) findViewById(R.id.iv_bottom);
        mGridView = (GridView) findViewById(R.id.gv_img);
        textNothing = (TextView) findViewById(R.id.iv_noimg);
        mGridView.setFastScrollEnabled(true);
        adapter = new ImageAdapter(this);
        mGridView.setAdapter(adapter);
        settingListView = (ListView) findViewById(R.id.lv);
        settingListView.setAdapter(new ArrayAdapter<String>(DragLayoutActivity.this,
                R.layout.draylayout_settting_list_item, new String[]{"My Profile", "My Setting", "Display Effect", "FeecBacks", "About Us"}));
        settingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
//                Util.toast(getApplicationContext(), "click " + position);
            }
        });
        titleIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mDragLayout.open();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImage();
    }

    private void loadImage() {
        new Invoker(new Callback() {
            @Override
            public boolean onRun() {
                adapter.addAll(Util.getGalleryPhotos(DragLayoutActivity.this));
                return adapter.isEmpty();
            }

            @Override
            public void onBefore() {
            }

            @Override
            public void onAfter(boolean b) {
                adapter.notifyDataSetChanged();
                if (b) {
                    textNothing.setVisibility(View.VISIBLE);
                } else {
                    textNothing.setVisibility(View.GONE);
//                    String s = "file://" + adapter.getItem(0);
//                    ImageLoader.getInstance().displayImage(s, titleIcon);
//                    ImageLoader.getInstance().displayImage(s, settingIcon);
                }
                shake();
            }
        }).start();

    }

    private void shake() {
        titleIcon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

}
