package com.example.blurtest.giftview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blurtest.R;
import com.squareup.picasso.Picasso;


/**
 * Created by yanghaij on 2016/3/30.
 */
public class GratuityView extends LinearLayout {
    private final static int MSG_REMOVE_ITEM = 0;
    private final static long ANIMATION_ADJUSTMENT = 300;

    private Context mContext;
    private LayoutInflater mInflater;
    int itemWidth;
    int itemHeight;
    private long appearAnimDuration = ANIMATION_ADJUSTMENT;
    private long disappearAnimDuration = ANIMATION_ADJUSTMENT;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REMOVE_ITEM:
                    removeGratuityItem((GratuityEntity) msg.obj);

                    break;
                default:
                    break;
            }
        }
    };

    public GratuityView(Context context) {
        super(context);
        init(context);
    }

    public GratuityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GratuityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initLayoutTransition();
    }

    private void measureChild() {
        if (getChildCount() < 1) {
            itemWidth = BaseDensityUtil.dip2px(mContext, 280f);
            itemHeight = BaseDensityUtil.dip2px(mContext, 65f);
            return;
        }

        View child = getChildAt(0);
        child.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY));
        itemWidth = child.getMeasuredWidth();
        itemHeight = child.getMeasuredHeight();
    }

    public void addGratuityEntity(final GratuityEntity entity) {
        if (!matchWithCurrentItem(entity)) {
            addItem(entity);
            Message msg = mHandler.obtainMessage(MSG_REMOVE_ITEM);
            msg.obj = entity;
            mHandler.sendMessageDelayed(msg, entity.getShowTime() * 1000);
        }
    }

    private boolean matchWithCurrentItem(GratuityEntity entity) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getTag() == null || child.getVisibility() != View.VISIBLE) {
                continue;
            }

            GratuityEntity tag = (GratuityEntity) child.getTag();
            if (tag.getUid().equals(entity.getUid()) && tag.getType().equals(entity.getType())) {
                //remove msg
                if (mHandler.hasMessages(MSG_REMOVE_ITEM, tag)) {
                    mHandler.removeMessages(MSG_REMOVE_ITEM, tag);
                }

                child.setTag(entity);
                updateCount((ViewGroup) child, entity);
                animateCountText((ViewGroup) child);

                //reset showtime
                Message msg = mHandler.obtainMessage(MSG_REMOVE_ITEM);
                msg.obj = entity;
                mHandler.sendMessageDelayed(msg, entity.getShowTime() * 1000);
                return true;
            }
        }

        return false;
    }

    private void animateCountText(ViewGroup viewgroup) {
        if (viewgroup == null) {
            return;
        }

        TextView count = (TextView) viewgroup.findViewById(R.id.count);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 2f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 2f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(count, pvhY, pvhZ).setDuration(400);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    private void updateCount(ViewGroup viewgroup, GratuityEntity entity) {
        if (viewgroup == null) {
            return;
        }

        TextView count = (TextView) viewgroup.findViewById(R.id.count);
        count.setText("X" + entity.getCount());
    }

    private void addItem(final GratuityEntity entity) {
        final ViewGroup child = (ViewGroup) mInflater.inflate(R.layout.av_red_bag_item, null);

        TextView nickname = (TextView) child.findViewById(R.id.member_nickname);
        TextView msg = (TextView) child.findViewById(R.id.member_msg);
        ImageView icon = (ImageView) child.findViewById(R.id.member_avatar);
        ImageView productImg = (ImageView) child.findViewById(R.id.gift_img);
        updateCount(child, entity);

        nickname.setText(entity.getNickname());
        msg.setText(entity.getDescription());
        if (!TextUtils.isEmpty(entity.getHeadUrl()) && entity.getHeadUrl().length() > 1) {
            Picasso.with(mContext).load(entity.getHeadUrl()).transform(new CropToCircleTransformation()).into(icon);
        }
        if (!TextUtils.isEmpty(entity.getGiftImgUrl()) && entity.getGiftImgUrl().length() > 1) {
            Picasso.with(mContext).load(entity.getGiftImgUrl()).into(productImg);
        }
        child.setTag(entity);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemHeight);
        if(getChildCount() > 0) {
            removeViewAt(0);
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getChildCount() == 1) {
                    addView(child, 1, params);
                } else if(getChildCount() == 0) {
                    addView(child, 0, params);
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(child.getParent() != null) {
                            animateCountText(child);
                        }
                    }
                }, appearAnimDuration + ANIMATION_ADJUSTMENT);
            }
        }, disappearAnimDuration);

        //after adding to view group, start shrink animation
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                animateCountText(child);
//            }
//        }, appearAnimDuration + ANIMATION_ADJUSTMENT);
    }

    private void removeGratuityItem(GratuityEntity entity) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getTag() == null || view.getTag() != entity) {
                continue;
            }

            final ViewGroup child = (ViewGroup) view;
            ObjectAnimator animator = ObjectAnimator.ofFloat(child, "alpha", 1.0f, 0f).setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //make this viewgroup invisible, it's not removed actually.
                    child.setVisibility(View.INVISIBLE);
                    child.removeAllViews();
                    child.setBackgroundColor(0);
                }
            });
            animator.start();
        }
    }

    private void initLayoutTransition() {
        measureChild();
        LayoutTransition mTransition = getLayoutTransition();
        setLayoutTransition(mTransition);

        appearAnimDuration = mTransition.getDuration(LayoutTransition.APPEARING);
        ObjectAnimator animator = ObjectAnimator.ofFloat(null, "translationX", -itemWidth, 0).
                setDuration(appearAnimDuration);
        animator.setInterpolator(new OvershootInterpolator(1.2f));
        mTransition.setAnimator(LayoutTransition.APPEARING, animator);

        disappearAnimDuration = mTransition.getDuration(LayoutTransition.DISAPPEARING);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(null, "translationY", 0, -itemHeight).
                setDuration(disappearAnimDuration);
        mTransition.setAnimator(LayoutTransition.DISAPPEARING, animator1);
    }


}
