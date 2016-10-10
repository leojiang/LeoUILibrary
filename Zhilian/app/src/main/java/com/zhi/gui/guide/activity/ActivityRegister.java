package com.zhi.gui.guide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.common.Constants;
import com.zhi.gui.guide.common.LogUtil;
import com.zhi.gui.guide.common.Preferences;
import com.zhi.gui.guide.common.Utilities;
import com.zhi.gui.guide.data.UserBasicInfo;
import com.zhi.gui.guide.network.FastJsonCommonHandler;
import com.zhi.gui.guide.network.NetRequestListener;
import com.zhi.gui.guide.network.NetworkTask;
import com.zhi.gui.guide.network.ThreadPoolManager;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityRegister extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ActivityRegister";
    private View mBtnRegister;
    private EditText mEditTextUserName;
    private EditText mEditTextPasswd;
    private EditText mEditTextConfPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditTextUserName = (EditText) findViewById(R.id.text_username);
        mEditTextPasswd = (EditText) findViewById(R.id.text_password);
        mEditTextConfPasswd = (EditText) findViewById(R.id.text_confirm_password);

        mBtnRegister = findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), ActivityAddBasicInfo.class));
//        register();
    }

    private boolean validation(String username, String passwd, String passwordConfirm) {
        return true;
    }

    private void register() {
        try {
            final String username = mEditTextUserName.getText().toString();
            String password = mEditTextPasswd.getText().toString();
            String passwordConfirm = mEditTextConfPasswd.getText().toString();
            if (!validation(username, password, passwordConfirm)) {
                return;
            }
            JSONObject json = new JSONObject();
            json.put("user_name", username);
            json.put("pass_wd", password);
            NetworkTask task = new NetworkTask(Constants.URL_BASE + Constants.SUFFIX_DIR_REGISTER, json);
            task.setNetRequestListener(new NetRequestListener() {
                @Override
                public void onSucceed(String json) {
                    LogUtil.d(TAG, "register succeed");
                    Preferences.setUserName(getApplicationContext(), username);
                    startActivity(new Intent(getApplicationContext(), ActivityAddBasicInfo.class));
                    finish();
                }

                @Override
                public void onFail(final int code) {
                    LogUtil.d(TAG, "register failed, code:" + code);
                }

                @Override
                public void onTimeout() {
                    Utilities.showToast(ActivityRegister.this, "登陆超时，请检查网络状况");
                }

                @Override
                public void onError(String reason) {
                    LogUtil.d(TAG, "login error:" + reason);
                }
            });
            ThreadPoolManager.getInstance().runTask(task);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
