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
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.Random;

public class DragLayoutActivity extends Activity {

    private DragLayout dl;
    private GridView gv_img;
    private ImageAdapter adapter;
    private ListView lv;
    private TextView tv_noimg;
    private ImageView iv_icon, iv_bottom;

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
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragListener() {
            @Override
            public void onOpen() {
                lv.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
                shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }

    private void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        gv_img = (GridView) findViewById(R.id.gv_img);
        tv_noimg = (TextView) findViewById(R.id.iv_noimg);
        gv_img.setFastScrollEnabled(true);
        adapter = new ImageAdapter(this);
        gv_img.setAdapter(adapter);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(DragLayoutActivity.this,
                R.layout.item_text, new String[]{"NewBee", "ViCi Gaming",
                "Evil Geniuses", "Team DK", "Invictus Gaming", "LGD",
                "Natus Vincere", "Team Empire", "Alliance", "Cloud9",
                "Titan", "Mousesports", "Fnatic", "Team Liquid",
                "MVP Phoenix", "NewBee", "ViCi Gaming",
                "Evil Geniuses", "Team DK", "Invictus Gaming", "LGD",
                "Natus Vincere", "Team Empire", "Alliance", "Cloud9",
                "Titan", "Mousesports", "Fnatic", "Team Liquid",
                "MVP Phoenix"}));
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Util.t(getApplicationContext(), "click " + position);
            }
        });
        iv_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
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
                // 转菊花
            }

            @Override
            public void onAfter(boolean b) {
                adapter.notifyDataSetChanged();
                if (b) {
                    tv_noimg.setVisibility(View.VISIBLE);
                } else {
                    tv_noimg.setVisibility(View.GONE);
                    String s = "file://" + adapter.getItem(0);
                    ImageLoader.getInstance().displayImage(s, iv_icon);
                    ImageLoader.getInstance().displayImage(s, iv_bottom);
                }
                shake();
            }
        }).start();

    }

    private void shake() {
        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

}
