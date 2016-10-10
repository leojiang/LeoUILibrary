package com.zhi.gui.guide.activity;

import java.util.ArrayList;
import java.util.List;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.adapter.CareerTargetAdapter;
import com.zhi.gui.guide.data.CareerTarget;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityChooseIndustry extends BaseActivity implements View.OnClickListener{
    private ListView mListView;
    private List<CareerTarget> mData = new ArrayList<CareerTarget>();
    private View mDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_career_target);
        ((TextView)findViewById(R.id.navigation_bar).findViewById(R.id.title)).setText("就业方向推荐");
        mListView = (ListView) findViewById(R.id.list);
        mDone = findViewById(R.id.choose_btn);
        mDone.setOnClickListener(this);
        initListView();
    }


    private void initListView() {
        for (int j = 0; j < 30; j++) {
            CareerTarget target = new CareerTarget(j, "职业 " + j, (39 - j) + "%");
            if (j == 0) {
                target.setCareerLevel(CareerTarget.LEVEL_PRIOR);
            } else if (j == 1) {
                target.setCareerLevel(CareerTarget.LEVEL_SECONDARY);
            }
            mData.add(target);
        }

        mListView.setAdapter(new CareerTargetAdapter(this, mData));
    }

    @Override
    public void onClick(View v) {
//        start
    }
}
