package com.zhi.gui.guide.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhi.gui.guide.R;
import com.zhi.gui.guide.common.Constants;
import com.zhi.gui.guide.view.CropToCircleTransformation;

public class FragmentPersonal extends FragmentBase implements View.OnClickListener {
    private View mViewUserInfo;
    private View mLonginPrompt;
    private View mEmptyText;
    private View mItemsVIew;
    private View mLoginBtn;
    private ImageView mAvatar;
    private View settings;


    @Override
    protected View createView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.fragment_personal, null);
        ((TextView)root.findViewById(R.id.navigation_bar).findViewById(R.id.title)).setText("我的");
        mViewUserInfo = root.findViewById(R.id.user_info);
        mAvatar = (ImageView) mViewUserInfo.findViewById(R.id.avatar);
        mLonginPrompt = root.findViewById(R.id.login_prompt);
        mEmptyText = root.findViewById(R.id.empty_text);
        mItemsVIew = root.findViewById(R.id.items);
        mLoginBtn = root.findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
        settings = root.findViewById(R.id.settings);
        settings.setOnClickListener(this);
        showView();

        Picasso.with(getActivity()).load(Uri.parse("http://www.photophoto.cn/m6/018/030/0180300376.jpg"))
                .transform(new CropToCircleTransformation()).into(mAvatar);

        return root;
    }

    private void showView() {
        if (isLoggedIn) {
            mViewUserInfo.setVisibility(View.VISIBLE);
            mLonginPrompt.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.GONE);
            mItemsVIew.setVisibility(View.VISIBLE);
        } else {
            mViewUserInfo.setVisibility(View.GONE);
            mLonginPrompt.setVisibility(View.VISIBLE);
            mEmptyText.setVisibility(View.VISIBLE);
            mItemsVIew.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onUserLogOut() {
        showView();
    }

    @Override
    protected void onUserLogIn() {
        showView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login_btn:
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                intent.putExtra(Constants.KEY_IS_LOGIN_FROM_MAIN, true);
                startActivity(intent);
                break;
            case R.id.settings:
                startActivity(new Intent(getActivity(), ActivitySettings.class));
                break;
            default:
                break;
        }
    }
}