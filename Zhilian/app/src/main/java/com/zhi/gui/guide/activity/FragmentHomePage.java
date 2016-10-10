package com.zhi.gui.guide.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.adapter.CompetenceListAdapter;
import com.zhi.gui.guide.adapter.IndustryListAdapter;
import com.zhi.gui.guide.common.Preferences;
import com.zhi.gui.guide.data.Competence;
import com.zhi.gui.guide.view.RefreshableView;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomePage extends FragmentBase implements RefreshableView.RefreshAndLoadListener {
    private ListView mListIndustry;
    private ListView mListRanking;
    private List<String> mIndustryList;
    private List<Competence> mCompetenceList;
    private CompetenceListAdapter mCompetenceListAdapter;
    private RefreshableView mRefreshableView;
    private Handler mHandler = new Handler();
    private View mLookAround;
    private View mCompetenceDetail;

    @Override
    protected View createView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.fragment_homepage, null);
        ((TextView)root.findViewById(R.id.navigation_bar).findViewById(R.id.title)).setText("首页");
        mLookAround = root.findViewById(R.id.look_around_page);
        mCompetenceDetail = root.findViewById(R.id.self_competence_page);
        showView(root);
        return root;
    }

    private void initListView() {
        mIndustryList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mIndustryList.add("行业类别" + i);
        }

        final IndustryListAdapter industryAdapter = new IndustryListAdapter(getActivity(), mIndustryList);
        mListIndustry.setAdapter(industryAdapter);
        mListIndustry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                industryAdapter.setSelectedItem(position);
                industryAdapter.notifyDataSetChanged();
            }
        });
        mCompetenceList = new ArrayList<Competence>();
        for (int j = 0; j < 30; j++) {
            mCompetenceList.add(new Competence(j, "路人" + j, (20 - j) * 100));
        }

        mCompetenceListAdapter = new CompetenceListAdapter(getActivity(), mCompetenceList);
        mListRanking.setAdapter(mCompetenceListAdapter);
    }


    private void showView(View root) {
        if (isLoggedIn) {
            mLookAround.setVisibility(View.GONE);
            mCompetenceDetail.setVisibility(View.VISIBLE);
        } else {
            mLookAround.setVisibility(View.VISIBLE);
            mCompetenceDetail.setVisibility(View.GONE);
            mListIndustry = (ListView) root.findViewById(R.id.list_industry);
            mListRanking = (ListView) root.findViewById(R.id.list_ranking);
            mRefreshableView = (RefreshableView) root.findViewById(R.id.refreshable_view);
            mRefreshableView.setOnRefreshListener(this, 0);
            initListView();
        }
    }

    @Override
    protected void onUserLogIn() {
        showView(mRootView);
    }

    @Override
    protected void onUserLogOut() {
        showView(mRootView);
    }

    @Override
    public void onRefresh() {
        try {
            Thread.sleep(3000);
            for (int i = 0; i < 2; i++) {
                mCompetenceList.add(0, new Competence(31, "leo" + 31, (20 - 31) * 100));
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCompetenceListAdapter.notifyDataSetChanged();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
        try {
            Thread.sleep(3000);
            for (int i = 0; i < 5; i++) {
                mCompetenceList.add(new Competence(2, "leo" + 31, (20 - 31) * 100));
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCompetenceListAdapter.notifyDataSetChanged();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
