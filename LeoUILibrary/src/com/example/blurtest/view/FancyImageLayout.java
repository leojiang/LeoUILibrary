package com.example.blurtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.blurtest.R;
import com.example.blurtest.giftview.BaseDensityUtil;
import com.squareup.picasso.Picasso;

/**
 * SukokuLayout is similar to a GridView that can hold at most nine children.
 *
 * Created by yanghaij on 2016/8/4.
 */
public class FancyImageLayout extends ViewGroup {
    private static final int ITEM_GRAP_IN_DIP = 2;

    private Context mContext;
    private int mItemGap;

    /**
     * two-dimension array, holds item's top and left positions
     */
    private int[][] mItemInfo;

    public FancyImageLayout(Context context) {
        super(context);
        init(context, null);
    }

    public FancyImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FancyImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mItemGap = BaseDensityUtil.dip2px(context, ITEM_GRAP_IN_DIP);
        setBackgroundColor(Color.WHITE);

        if (attrs != null) {
            TypedArray typedArr = context.obtainStyledAttributes(attrs,
                    R.styleable.FancyImageLayout);
            mItemGap = (int) typedArr.getDimension(R.styleable.FancyImageLayout_item_gap, ITEM_GRAP_IN_DIP);
            typedArr.recycle();
        }
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
     * @param totalWidth width
     * @return height
     */
    private int doMeasurement(int totalWidth) {
        int count = getChildCount();
        if (count <= 0) {
            return 0;
        }

        mItemInfo = new int[count][4];

        switch (count) {
            case 1:
                return measureItems5Excluded(totalWidth, 1);
            case 2:
            case 3:
            case 4:
                return measureItems5Excluded(totalWidth, 2);
            case 6:
                return measureItemsFirstBigger(totalWidth, 3, 2);
            case 13:
                return measureItemsFirstBigger(totalWidth, 4, 2);
            default:
                return measureItems5Excluded(totalWidth, 3);
        }
    }

    /**
     * The first item it multi times size of normal items.
     * Be careful when using this method, cause there're several requirements
     * should be fullfilled.
     *
     * @param totalWidth the width of FancyImageLayout
     * @param column     column numbers of normal items
     * @param multiple   times of the first item's size compares to normal items
     * @return height of FancyImageLayout
     */
    private int measureItemsFirstBigger(int totalWidth, int column, int multiple) {
        if (multiple >= column) {
            return 0;
        }

        int count = getChildCount();
        int normalItemSize = (totalWidth - mItemGap * (column - 1)) / column;
        int fistItemSize = normalItemSize * multiple + mItemGap * (multiple - 1);
        int rightTopPartColumn = column - multiple;
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                //the first one is bigger than others
                mItemInfo[i][0] = 0;
                mItemInfo[i][1] = 0;
                mItemInfo[i][2] = fistItemSize;
                mItemInfo[i][3] = fistItemSize;
            } else {
                if (i <= rightTopPartColumn * multiple) {
                    //right-top area
                    mItemInfo[i][0] = fistItemSize + mItemGap + (normalItemSize + mItemGap) * ((i - 1) % rightTopPartColumn);
                    mItemInfo[i][1] = (normalItemSize + mItemGap) * ((i - 1) / rightTopPartColumn);
                } else {
                    //bottom area
                    int subIndex = i - rightTopPartColumn * multiple - 1;
                    mItemInfo[i][0] = (normalItemSize + mItemGap) * (subIndex % column);
                    mItemInfo[i][1] = fistItemSize + mItemGap + (normalItemSize + mItemGap) * (subIndex / column);
                }
                mItemInfo[i][2] = mItemInfo[i][0] + normalItemSize;
                mItemInfo[i][3] = mItemInfo[i][1] + normalItemSize;
            }
        }

        return mItemInfo[count - 1][3];
    }

    private static final int SIZE_RATIO = 2;

    private int measureItems5Excluded(int totalWidth, int column) {
        int count = getChildCount();
        int firstLineNumb = count % column;
        int firstLineHeight = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (i < firstLineNumb) {
                //calculate item size of items in first line
                int itemWidth = (totalWidth - mItemGap * (firstLineNumb - 1)) / firstLineNumb;
                int itemHeight = itemWidth;

                //if first line has more than one item and total item more than one
                if (firstLineNumb == 1 && count > 1) {
                    itemHeight = itemWidth / SIZE_RATIO; //or itemWidth / (colunm - 1)
                }

                //save height of first line item.
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
                mItemInfo[i][1] = firstLineHeight + (firstLineHeight > 0 ? mItemGap : 0) +
                        (i - firstLineNumb) / column * (itemMaxSize + mItemGap);
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
            getChildAt(i).layout(mItemInfo[i][0], mItemInfo[i][1], mItemInfo[i][2], mItemInfo[i][3]);
        }
    }

    public void setImageSources(Object[] urls) {
        mItemInfo = new int[urls.length][4];

        for (Object url : urls) {
            addImageView((String)url);
        }
    }

    public void setImageSources(String[] urls) {
        mItemInfo = new int[urls.length][4];

        for (String url : urls) {
            addImageView(url);
        }

    }

    private void addImageView(String url) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mContext).load(url).into(imageView);
        addView(imageView);
    }

}
