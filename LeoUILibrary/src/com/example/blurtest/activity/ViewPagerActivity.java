package com.example.blurtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.blurtest.R;
import com.example.blurtest.pagertransformers.AccordionTransformer;
import com.example.blurtest.pagertransformers.CubeTransformer;
import com.example.blurtest.pagertransformers.DefaultTransformer;
import com.example.blurtest.pagertransformers.DepthPageTransformer;
import com.example.blurtest.pagertransformers.InRightDownTransformer;
import com.example.blurtest.pagertransformers.InRightUpTransformer;
import com.example.blurtest.pagertransformers.RotateDownPageTransformer;
import com.example.blurtest.pagertransformers.RotateTransformer;
import com.example.blurtest.pagertransformers.ZoomOutPageTransformer;

import java.util.ArrayList;

public class ViewPagerActivity extends Activity {

    ViewPager pager = null;
    ArrayList<View> viewContainter = new ArrayList<View>();
    public String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_viewpager);

        pager = (ViewPager) this.findViewById(R.id.viewpager);

        resetView();

    }

    private void getViews() {
        viewContainter.clear();
        View view1 = LayoutInflater.from(this).inflate(R.layout.tab1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.tab2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.tab3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.tab4, null);
        viewContainter.add(view1);
        viewContainter.add(view2);
        viewContainter.add(view3);
        viewContainter.add(view4);
    }

    private void resetView() {
        pager.removeAllViews();
        getViews();
        pager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return viewContainter.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(viewContainter.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewContainter.get(position));
                return viewContainter.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }
        });
        pager.setCurrentItem(0);
        pager.setPageTransformer(true, new DefaultTransformer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Menu.FIRST + 1, 1, "Depth Effect");
        menu.add(0, Menu.FIRST + 2, 2, "Zoom Effect");
        menu.add(0, Menu.FIRST + 3, 3, "Cube Effect");
        menu.add(0, Menu.FIRST + 4, 4, "Rotate Down Effect");
        menu.add(0, Menu.FIRST + 5, 5, "According Effect");
        menu.add(0, Menu.FIRST + 6, 6, "Default Effect");
        menu.add(0, Menu.FIRST + 7, 7, "InRightDown Effect");
        menu.add(0, Menu.FIRST + 8, 8, "InRightUp Effect");
        menu.add(0, Menu.FIRST + 9, 9, "Rotate Effect");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        resetView();
        int id = item.getItemId();
        if (id == Menu.FIRST + 1) {
            pager.setPageTransformer(true, new DepthPageTransformer());
        } else if (id == Menu.FIRST + 2) {
            pager.setPageTransformer(true, new ZoomOutPageTransformer());
        } else if (id == Menu.FIRST + 3) {
            pager.setPageTransformer(true, new CubeTransformer());
        } else if (id == Menu.FIRST + 4) {
            pager.setPageTransformer(true, new RotateDownPageTransformer());
        } else if (id == Menu.FIRST + 5) {
            pager.setPageTransformer(true, new AccordionTransformer());
        } else if (id == Menu.FIRST + 6) {
            pager.setPageTransformer(true, new DefaultTransformer());
        } else if (id == Menu.FIRST + 7) {
            pager.setPageTransformer(true, new InRightDownTransformer());
        } else if (id == Menu.FIRST + 8) {
            pager.setPageTransformer(true, new InRightUpTransformer());
        } else if (id == Menu.FIRST + 9) {
            pager.setPageTransformer(true, new RotateTransformer());
        }
        return super.onContextItemSelected(item);
    }

}
