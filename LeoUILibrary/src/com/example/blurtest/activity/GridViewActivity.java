package com.example.blurtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blurtest.R;
import com.example.blurtest.draglayouttomenu.Callback;
import com.example.blurtest.draglayouttomenu.ImageAdapter;
import com.example.blurtest.draglayouttomenu.Invoker;
import com.example.blurtest.draglayouttomenu.Util;

import java.util.ArrayList;

public class GridViewActivity extends Activity {
    private GridView mGridView;
    private TextView mTextView;
    private ImageAdapter mAdapter;
    private ViewGroup mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        Util.initImageLoader(this);

        mGridView = (GridView) findViewById(R.id.grid_view);
        mTextView = (TextView) findViewById(R.id.text_load_more);
        mContent = (ViewGroup) findViewById(R.id.content);
        mAdapter = new ImageAdapter(this);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(new AutoLoadListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImage();
    }

    private ArrayList<String> mList;

    private void loadImage() {
        new Invoker(new Callback() {
            @Override
            public boolean onRun() {
                mList = Util.getGalleryPhotos(GridViewActivity.this);
                for (int i = 0; i < 15; i++) {
                    mAdapter.addItem(mList.get(i));
                }
//                mAdapter.addAll(Util.getGalleryPhotos(GridViewActivity.this));
                return mAdapter.isEmpty();
            }

            @Override
            public void onBefore() {
            }

            @Override
            public void onAfter(boolean b) {
                mAdapter.notifyDataSetChanged();
            }
        }).start();
    }

    public class AutoLoadListener implements AbsListView.OnScrollListener {

//        public interface AutoLoadCallBack {
//            void execute();
//        }

        private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;
//        private AutoLoadCallBack mCallback;
//
//        public AutoLoadListener(AutoLoadCallBack callback) {
//            this.mCallback = callback;
//        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {

            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                //滚动到底部
                if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    View v = view.getChildAt(view.getChildCount() - 1);
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                    int y = location[1];
                    int itemheight = v.getHeight();

                    int[] locationParent = new int[2];
                    view.getLocationOnScreen(locationParent);
                    int yParent = locationParent[1];
                    int parentHeight = view.getHeight();

                    if (view.getLastVisiblePosition() != getLastVisiblePosition && lastVisiblePositionY != y)//第一次拖至底部
                    {
                        if(location[1] + itemheight >= yParent + parentHeight) {
//                            Toast.makeText(view.getContext(), "已经拖动至底部，再次拖动即可翻页", Toast.LENGTH_SHORT).show();
                            getLastVisiblePosition = view.getLastVisiblePosition();
                            lastVisiblePositionY = y;
                            for (int i = 0; i < 15; i++) {
                                mAdapter.addItem(mList.get(i));
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            int j = 1;
                        }
                        return;
                    }
//                    else if (view.getLastVisiblePosition() == getLastVisiblePosition && lastVisiblePositionY == y)//第二次拖至底部
//                    {
//                        mCallback.execute();
//                        mContent.scrollTo(0, mContent.getHeight() + 90);
//                    }
                }

                //未滚动到底部，第二次拖至底部都初始化
                getLastVisiblePosition = 0;
                lastVisiblePositionY = 0;
            }
        }

        public void onScroll(AbsListView view, int fristVisibleChild, int visibleCount, int totalChildCount) {
//            if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//                View v = view.getChildAt(view.getChildCount() - 1);
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
//                int y = location[1];
//                int[] locationParent = new int[2];
//                view.getLocationOnScreen(locationParent);
//                int yParent = locationParent[1];
//                if (y + v.getHeight() == yParent) {
//                    Toast.makeText(view.getContext(), "已经拖动至底部，再次拖动即可翻页", Toast.LENGTH_SHORT).show();
//                }
//
//            }
        }
    }
}
