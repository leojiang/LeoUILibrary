package com.zhi.gui.guide.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhi.gui.guide.common.Preferences;

public abstract class FragmentBase extends Fragment {
    public static String BROADCAST_USER_LOG_IN = "broadcast_user_log_in";
    public static String BROADCAST_USER_LOG_OUT = "broadcast_user_log_out";
    protected View mRootView = null;
    protected boolean isLoggedIn = false;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BROADCAST_USER_LOG_IN.equals(action)) {
                isLoggedIn = Preferences.getUserLoggedInState(getActivity().getApplicationContext());
                onUserLogIn();
            } else if (BROADCAST_USER_LOG_OUT.equals(action)) {
                isLoggedIn = Preferences.getUserLoggedInState(getActivity().getApplicationContext());
                onUserLogOut();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_USER_LOG_IN);
        filter.addAction(BROADCAST_USER_LOG_OUT);
        getActivity().registerReceiver(mReceiver, filter);

        isLoggedIn = Preferences.getUserLoggedInState(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = createView(inflater);
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    protected abstract View createView(LayoutInflater inflater);

    protected abstract void onUserLogIn();

    protected abstract void onUserLogOut();
}
