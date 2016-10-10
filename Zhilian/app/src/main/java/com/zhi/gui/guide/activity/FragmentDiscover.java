package com.zhi.gui.guide.activity;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.common.LogUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentDiscover extends FragmentBase {

    @Override
    protected View createView(LayoutInflater inflater) {
        View root= inflater.inflate(R.layout.fragment_discovery, null);
        ((TextView)root.findViewById(R.id.navigation_bar).findViewById(R.id.title)).setText("发现");
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i("leojiang", "onActivityCreated");
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.i("leojiang", "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.i("leojiang", "onDetach");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i("leojiang", "onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i("leojiang", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("leojiang", "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.i("leojiang", "onStop");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("leojiang", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("leojiang", "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i("leojiang", "onDestroyView");
    }

    @Override
    protected void onUserLogIn() {

    }

    @Override
    protected void onUserLogOut() {

    }
}