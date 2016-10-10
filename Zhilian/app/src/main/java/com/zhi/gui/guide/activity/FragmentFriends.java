package com.zhi.gui.guide.activity;

import java.util.ArrayList;
import java.util.List;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.view.HomePageViewAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentFriends extends FragmentBase {

    private ViewPager mViewPager;
    private HomePageViewAdapter mViewPagerAdapter;
    private List<Fragment> mFragmentList;

    @Override
    protected View createView(LayoutInflater inflater) {
        View root= inflater.inflate(R.layout.fragment_friends, null);
        ((TextView)root.findViewById(R.id.navigation_bar).findViewById(R.id.title)).setText("圈圈");

        mViewPager = (ViewPager) root.findViewById(R.id.view_pager);

        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new FragmentInternship());
        mFragmentList.add(new FragmentPersonal());
        mViewPagerAdapter = new HomePageViewAdapter(getChildFragmentManager(), mFragmentList,
                null);

        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(0);
        return root;
    }


    @Override
    protected void onUserLogIn() {

    }

    @Override
    protected void onUserLogOut() {

    }
}