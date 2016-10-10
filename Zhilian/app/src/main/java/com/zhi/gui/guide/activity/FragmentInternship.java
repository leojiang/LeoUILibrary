package com.zhi.gui.guide.activity;

import java.util.ArrayList;
import java.util.List;

import com.zhi.gui.guide.R;
import com.zhi.gui.guide.adapter.InternshipFullAdapter;
import com.zhi.gui.guide.data.InternshipFull;
import com.zhi.gui.guide.view.RefreshableView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentInternship extends FragmentBase
        implements RefreshableView.RefreshAndLoadListener, AdapterView.OnItemClickListener {

    private ListView mListInternships;
    private List<InternshipFull> mInternshipFullList;
    private InternshipFullAdapter mFullAdapter;
    private RefreshableView mRefreshableView;
    private View mCompetenceView;
    private View mTargetList;
    private View mAdvertisement;
    private View mNavigation;
    private View mSearchBar;
    private View mFull;
    private boolean mIsCompetenceEnough = true;

    @Override
    protected View createView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.fragment_internship, null);
        ((TextView)root.findViewById(R.id.navigation).findViewById(R.id.title)).setText("实习");
        mNavigation = root.findViewById(R.id.navigation);
        mFull = root.findViewById(R.id.intership_full);
        mCompetenceView = root.findViewById(R.id.competence_info);
        mTargetList = root.findViewById(R.id.target_list);
        mSearchBar = mFull.findViewById(R.id.search_bar);
        mAdvertisement = mFull.findViewById(R.id.advertisement);
        mListInternships = (ListView) mFull.findViewById(R.id.list_internships);
        mRefreshableView = (RefreshableView) mFull.findViewById(R.id.refreshable_view);
        mListInternships.setOnItemClickListener(this);
        mRefreshableView.setOnRefreshListener(this, 0);
        showView();
        initListView();
        return root;
    }

    private void showView() {
        if (isLoggedIn) {
            mCompetenceView.setVisibility(View.VISIBLE);
            if (mIsCompetenceEnough) {
                mRefreshableView.setVisibility(View.VISIBLE);
                mAdvertisement.setVisibility(View.VISIBLE);
                mSearchBar.setVisibility(View.VISIBLE);
                mNavigation.setVisibility(View.GONE);
                mTargetList.setVisibility(View.GONE);
            } else {
                mRefreshableView.setVisibility(View.GONE);
                mSearchBar.setVisibility(View.GONE);
                mAdvertisement.setVisibility(View.GONE);
                mNavigation.setVisibility(View.VISIBLE);
                mTargetList.setVisibility(View.VISIBLE);
            }
        } else {
            mRefreshableView.setVisibility(View.VISIBLE);
            mSearchBar.setVisibility(View.VISIBLE);
            mNavigation.setVisibility(View.GONE);
            mAdvertisement.setVisibility(View.VISIBLE);
            mCompetenceView.setVisibility(View.GONE);
            mTargetList.setVisibility(View.GONE);
        }
    }

    private void initListView() {
        mInternshipFullList = new ArrayList<InternshipFull>();
        for (int j = 0; j < 30; j++) {
            if (j < 5) {
                mInternshipFullList.add(new InternshipFull("某科技公司", "软件开发工程师", "南京",
                        (20 - j) * 100, "http://img1.imgtn.bdimg.com/it/u=781850496,3957428669&fm=21&gp=0.jpg", "15-25K", true));
            } else {
                mInternshipFullList.add(new InternshipFull("某科技公司", "软件开发工程师", "南京",
                        (20 - j) * 100, "http://down.tutu001.com/d/file/20110721/05f70880ffe6f19251c7c523df_560.jpg", "15-25K"));
            }
        }
        mFullAdapter = new InternshipFullAdapter(getActivity(), mInternshipFullList);
        mFullAdapter.setmIsCompetenceEnough(mIsCompetenceEnough);
        mListInternships.setAdapter(mFullAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), ActivityInternshipDetail.class));
    }

    @Override
    public void onRefresh() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {

    }

    @Override
    protected void onUserLogOut() {
        showView();
    }

    @Override
    protected void onUserLogIn() {
        showView();
    }
}