package com.example.blurtest.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.blurtest.giftview.BaseDensityUtil;
import com.squareup.picasso.Picasso;

/**
 * SukokuLayout is similar to a GridView that can hold at most nine children.
 * <p/>
 * Created by yanghaij on 2016/8/4.
 */
public class FancyImageLayout extends ViewGroup {
    private static final int ITEM_GRAP_IN_DIP = 4;


    private Context mContext;
    private String[] mImageUrls;

    private int mItemGap;
    private int mWidth;

    /**
     * two-dimension array, holds item's top and left
     */
    private int[][] mItemInfo;

    public FancyImageLayout(Context context) {
        super(context);
        init(context);
    }

    public FancyImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FancyImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mItemGap = BaseDensityUtil.dip2px(context, ITEM_GRAP_IN_DIP);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = doMeasurement(width);

        setMeasuredDimension(width, height);
    }

    /**
     * calculate each item's size, and return the height of FancyImageLayout.
     *
     * @param totalWidth
     * @return
     */
    private int doMeasurement(int totalWidth) {
        if (mImageUrls == null || mImageUrls.length <= 0) {
            return 0;
        }

        int count = mImageUrls.length;
        mItemInfo = new int[count][4];

        switch (count) {
            case 1:
                return measureItems5Excluded(totalWidth, 1);
            case 2:
                return measureItems5Excluded(totalWidth, 2);
            case 3:
                return measureItems5Excluded(totalWidth, 2);
            case 4:
                return measureItems5Excluded(totalWidth, 2);
            case 5:
                return measureItems5Excluded(totalWidth, 3);
            case 6:
                return measure6Item(totalWidth);
            case 7:
                return measureItems5Excluded(totalWidth, 3);
            case 8:
                return measureItems5Excluded(totalWidth, 3);
            case 9:
                return measureItems5Excluded(totalWidth, 3);
        }
        return 0;
    }


    private int measure6Item(int totalWidth) {
        return 0;
    }

    private static final int SIZE_RATIO = 2;

    private int measureItems5Excluded(int totalWidth, int column) {
        int count = getChildCount();
        int firstLineNumb = count % column;
        int firstLineHeight = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            //calculate item size of items in first line
            if (i < firstLineNumb) {
                int itemWidth = (totalWidth - mItemGap * (firstLineNumb - 1)) / firstLineNumb;
                int itemHeight = itemWidth;

                //if first line has more than one item and total item more than one
                if (firstLineNumb == 1 && count > 1) {
                    itemHeight = itemWidth / SIZE_RATIO; //or itemWidth / (colunm - 1)
                }

                //save height for first line item, added to other lines top value.
                firstLineHeight = itemHeight;

                int measureSpecWidth = MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY);
                int measureSpecHeight = MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY);
                child.measure(measureSpecWidth, measureSpecHeight);

                mItemInfo[i][0] = (itemWidth + mItemGap) * i;
                mItemInfo[i][1] = 0;
            } else {
                int itemMaxSize = (totalWidth - mItemGap * (column - 1)) / column;
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(itemMaxSize, MeasureSpec.EXACTLY);
                child.measure(makeMeasureSpec, makeMeasureSpec);

                mItemInfo[i][0] = (itemMaxSize + mItemGap) * ((i - firstLineNumb) % column);
                int top = firstLineHeight + (firstLineHeight > 0 ? mItemGap : 0) + (i - firstLineNumb) / column * (itemMaxSize + mItemGap);
                mItemInfo[i][1] = top;
            }

            mItemInfo[i][2] = mItemInfo[i][0] + child.getMeasuredWidth();
            mItemInfo[i][3] = mItemInfo[i][1] + child.getMeasuredHeight();
        }

        return mItemInfo[count - 1][3];
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count <= 0) {
            return;
        }

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            child.layout(mItemInfo[i][0], mItemInfo[i][1], mItemInfo[i][2], mItemInfo[i][3]);
        }
    }

    public void setImageSources(String[] urls) {
        mImageUrls = urls;
        mItemInfo = new int[mImageUrls.length][4];


        for (int i = 0; i < mImageUrls.length; i++) {
            addImageView(mImageUrls[i]);
        }
    }

    private void addImageView(String url) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mContext).load(url).into(imageView);
        addView(imageView);
    }

}
