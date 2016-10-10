package com.zhi.gui.guide.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zhi.gui.guide.common.Preferences;

public class ActivitySplash extends BaseActivity {
    @Override
    protected void onResume() {
        super.onResume();
        if (Preferences.getUserLoggedInState(this)) {
            startActivity(new Intent(getApplicationContext(), ActivityMainTab.class));
        } else {
            startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
        }
        finish();
    }
}
