package com.zhi.gui.guide.activity;

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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ActivityAddBasicInfo extends BaseActivity implements OnClickListener {
    private static final String TAG = "ActivityAddBasicInfo";

    private View mNextStep;

    private EditText mTextName;
    private EditText mTextNickname;
    private EditText mTextCollege;
    private EditText mTextMajor;
    private EditText mTextDegree;
    private EditText mTextGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_basic_info);

        ((TextView) findViewById(R.id.navigation_bar).findViewById(R.id.title)).setText("基本信息");

        mNextStep = findViewById(R.id.button_next_step);
        mNextStep.setOnClickListener(this);

        mTextName = (EditText) findViewById(R.id.name);
        mTextNickname = (EditText) findViewById(R.id.nickname);
        mTextCollege = (EditText) findViewById(R.id.college);
        mTextMajor = (EditText) findViewById(R.id.major);
        mTextDegree = (EditText) findViewById(R.id.degree);
        mTextGrade = (EditText) findViewById(R.id.grade);
    }

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.button_next_step) {
//            submitBasicInfo();
            startActivity(new Intent(ActivityAddBasicInfo.this, ActivityChooseIndustry.class));
        }
    }

    private boolean validation(String name, String nickname, String college, String major, String degree, String grade) {
        return true;
    }

    private void submitBasicInfo() {
        try {
            final String name = mTextName.getText().toString();
            final String nickname = mTextNickname.getText().toString();
            final String college = mTextCollege.getText().toString();
            final String major = mTextMajor.getText().toString();
            final String degree = mTextDegree.getText().toString();
            final String grade = mTextGrade.getText().toString();

            if (!validation(name, nickname, college, major, degree, grade)) {
                return;
            }

            JSONObject json = new JSONObject();
            json.put("user_name", Preferences.getUserName(getApplicationContext()));
            json.put("name", name);
            json.put("nickname", nickname);
            json.put("college", college);
            json.put("major", major);
            json.put("education", degree);
            json.put("grade", grade);
            NetworkTask task = new NetworkTask(Constants.URL_BASE + Constants.SUFFIX_DIR_DETAIL, json);
            task.setNetRequestListener(new NetRequestListener() {
                @Override
                public void onSucceed(String json) {
                    LogUtil.d(TAG, "submit basic information succeed");
                    UserBasicInfo info = new UserBasicInfo();
                    info.name = name;
                    info.nick_name = nickname;
                    info.college = college;
                    info.education = degree;
                    info.major = major;
                    info.grade = grade;
                    Preferences.setUserBasicInfo(getApplicationContext(), info);

                    startActivity(new Intent(ActivityAddBasicInfo.this, ActivityChooseIndustry.class));
                    finish();
                }

                @Override
                public void onFail(final int code) {
                    LogUtil.d(TAG, "submit basic information failed, code:" + code);
                    Utilities.showToast(ActivityAddBasicInfo.this, "submit basic information failed, code:" + code);
                }

                @Override
                public void onTimeout() {
                    Utilities.showToast(ActivityAddBasicInfo.this, "登陆超时，请检查网络状况");
                }

                @Override
                public void onError(String reason) {
                    LogUtil.d(TAG, "submit basic information error:" + reason);
                    Utilities.showToast(ActivityAddBasicInfo.this, reason);
                }
            });
            ThreadPoolManager.getInstance().runTask(task);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
