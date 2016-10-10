package com.zhi.gui.guide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhi.gui.guide.R;

public class ActivityInternshipDetail extends BaseActivity implements View.OnClickListener {

    private View mCompanyDescription;
    private View mBtnFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern_detail);
        ((TextView)findViewById(R.id.navigation_bar).findViewById(R.id.title)).setText("实习");
        mCompanyDescription = findViewById(R.id.company_description);
        mCompanyDescription.setOnClickListener(this);

        mBtnFollow = findViewById(R.id.follow);
        mBtnFollow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.company_description:
                startActivity(new Intent(this, CompanyActivity.class));
                break;
            case R.id.follow:
                break;
        }

    }
}
