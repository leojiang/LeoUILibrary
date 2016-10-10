package com.zhi.gui.guide.activity;

import com.zhi.gui.guide.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMainTab extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private LayoutInflater mLayoutInflater;
    private int mBackKeyPressedCount = 0;

    private Class fragmentArray[] = {FragmentHomePage.class, FragmentFriends.class,
            FragmentDiscover.class, FragmentInternship.class, FragmentPersonal.class};

    private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_message_btn,
            R.drawable.tab_square_btn, R.drawable.tab_selfinfo_btn, R.drawable.tab_more_btn};

    private String[] mTextviewArray = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mTextviewArray = getResources().getStringArray(R.array.tab_names);

        setContentView(R.layout.main_tab_layout);

        initView();
    }

    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(0);
        }
        updateTabBackground(mTabHost);

        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
            }
        });

        mTabHost.setCurrentTab(0);
    }

    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    private void updateTabBackground(final TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_tab_background));
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackKeyPressedCount == 0) {
            mBackKeyPressedCount++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBackKeyPressedCount = 0;
                }
            }, 2000);
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
