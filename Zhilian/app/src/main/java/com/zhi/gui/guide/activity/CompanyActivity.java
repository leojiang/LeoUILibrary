package com.zhi.gui.guide.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.adapter.ImageListAdapter;
import com.zhi.gui.guide.adapter.InternshipFullAdapter;
import com.zhi.gui.guide.data.InternshipFull;
import com.zhi.gui.guide.view.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

public class CompanyActivity extends BaseActivity implements View.OnClickListener {
    private HorizontalListView mGallery;
    private ListView mJobLIstView;
    private InternshipFullAdapter mFullAdapter;
    private List<InternshipFull> mInternshipFullList;

    private TextView mTabCompany;
    private TextView mTabJobs;
    private View mCompanInfo;
    private View mJobsInfo;
    private List<String> mImageURLs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_detail);
        ((TextView)findViewById(R.id.navigation_bar).findViewById(R.id.title)).setText("实习");

        mGallery = (HorizontalListView) findViewById(R.id.gallery);
        mJobLIstView = (ListView) findViewById(R.id.job_list);
        mTabCompany = (TextView)findViewById(R.id.company);
        mTabJobs = (TextView)findViewById(R.id.jobs);
        mCompanInfo = findViewById(R.id.company_info);
        mJobsInfo = findViewById(R.id.jobs_info);
        mTabCompany.setOnClickListener(this);
        mTabJobs.setOnClickListener(this);

        initGallery();
        initListView();
    }

    private void initGallery() {
        mImageURLs.add("http://pic37.nipic.com/20140121/7180085_092436783000_2.jpg");
        mImageURLs.add("http://a2.att.hudong.com/14/06/20300001244055130863068274072.jpg");
        mImageURLs.add("http://img2.imgtn.bdimg.com/it/u=1062894030,187965380&fm=21&gp=0.jpg");
        mImageURLs.add("http://mg.soupingguo.com/bizhi/big/10/054/203/10054203.jpg");
        mImageURLs.add("http://a0.att.hudong.com/51/47/01300000098168123480478377186.jpg");
        mImageURLs.add("http://pic33.nipic.com/20131011/2531170_115542187000_2.jpg");
        mImageURLs.add("http://pica.nipic.com/2007-11-15/20071115132542605_2.jpg");
        mImageURLs.add("http://pic20.nipic.com/20120406/6113596_150042568000_2.jpg");
        mImageURLs.add("http://5.66825.com/download/pic/000/326/719d548e256e2677aeb91a7a9c464571.jpg");
        mImageURLs.add("http://i1.17173cdn.com/9ih5jd/YWxqaGBf/forum/images/2014/08/01/231302zkvvgn1ng0lnglzn.jpg");
        mImageURLs.add("http://www.bz55.com/uploads/allimg/101227/1_101227162638_1.jpg");
        mImageURLs.add("http://www.impcas.ac.cn/kxcb/kxtp/201010/W020101028494020681011.jpg");


        mGallery.setAdapter(new ImageListAdapter(this, mImageURLs));
    }

    private void initListView() {
        initViewWhenNotLoggedIn();
    }


    private void initViewWhenNotLoggedIn() {
        mInternshipFullList = new ArrayList<InternshipFull>();
        for (int j = 0; j < 30; j++) {
            mInternshipFullList.add(new InternshipFull("某科技公司", "软件开发工程师", "南京",
                    (20 - j) * 100, "http://picm.bbzhi.com/dongmanbizhi/gaodazhuomian/game_manwall_178671_m.jpg", "15-25K"));
        }
        mFullAdapter = new InternshipFullAdapter(this, mInternshipFullList);
        mJobLIstView.setAdapter(mFullAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.company:
                selectCompanInfo();
                break;
            case R.id.jobs:
                selectJobsInfo();
                break;
        }
    }

    private void selectCompanInfo() {
        mTabCompany.setBackgroundColor(getResources().getColor(R.color.color_shllow_blue));
        mTabCompany.setTextColor(getResources().getColor(R.color.color_bg_white));
        mTabJobs.setBackgroundColor(getResources().getColor(R.color.color_bg_white));
        mTabJobs.setTextColor(getResources().getColor(R.color.text_black));
        mCompanInfo.setVisibility(View.VISIBLE);
        mJobsInfo.setVisibility(View.GONE);
    }

    private void selectJobsInfo() {
        mTabCompany.setBackgroundColor(getResources().getColor(R.color.color_bg_white));
        mTabCompany.setTextColor(getResources().getColor(R.color.text_black));
        mTabJobs.setBackgroundColor(getResources().getColor(R.color.color_shllow_blue));
        mTabJobs.setTextColor(getResources().getColor(R.color.color_bg_white));
        mCompanInfo.setVisibility(View.GONE);
        mJobsInfo.setVisibility(View.VISIBLE);
    }
}
