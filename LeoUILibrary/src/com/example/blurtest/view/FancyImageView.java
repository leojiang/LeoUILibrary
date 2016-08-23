package com.example.blurtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.blurtest.R;
import com.example.blurtest.giftview.BaseDensityUtil;
import com.squareup.picasso.Picasso;

/**
 * FancyImageView can display more than one image and layout images
 * properly to make the view looks nice.
 *
 * Created by yanghaij on 2016/8/4.
 */
public class FancyImageView extends ViewGroup {
    /**
     * Default gap between items, unit is dip.
     */
    private static final int ITEM_GAP_IN_DIP = 2;

    private static final int SIZE_RATIO = 2;
    private Context mContext;

    /**
     * Item gap, unit pixel.
     */
    private int mItemGap;

    /**
     * two-dimension array, holds item's top and left positions
     */
    private int[][] mItemInfo;

    public FancyImageView(Context context) {
        super(context);
        init(context, null);
    }

    public FancyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FancyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mItemGap = BaseDensityUtil.dip2px(context, ITEM_GAP_IN_DIP);

        if (attrs != null) {
            TypedArray typedArr = context.obtainStyledAttributes(attrs,
                    R.styleable.FancyImageView);
            mItemGap = (int) typedArr.getDimension(R.styleable.FancyImageView_item_gap, ITEM_GAP_IN_DIP);
            typedArr.recycle();
        }
    }

    /**
     * Set gap between items, unit pixel.
     *
     * @param gap gap size
     */
    public void setItemGap(int gap) {
        mItemGap = gap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, doMeasurement(width));
    }

    /**
     * calculate each item's size, and return the height of FancyImageView.
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
                return measureItems(totalWidth, 1, 0);
            case 2:
            case 3:
            case 4:
                return measureItems(totalWidth, 2, 0);
            case 6:
                return measureItems(totalWidth, 3, 2);
            default:
                return measureItems(totalWidth, 3, 0);
        }
    }

    private int measureItems(int totalWidth, int column, int multiple) {
        if (multiple >= column) {
            return 0;
        }

        int count = getChildCount();
        int normalItemSize = (totalWidth - mItemGap * (column - 1)) / column;
        int firstItemSize = normalItemSize * multiple + mItemGap * (multiple - 1);
        int rightTopPartColumn = column - multiple;
        int firstLineNumb = count % column;
        int firtLineItemWidth = firstLineNumb > 0 ? (totalWidth - mItemGap * (firstLineNumb - 1)) / firstLineNumb : 0;
        int firstLineHeight = 0;
        int left, top, right, bottom;

        for (int i = 0; i < count; i++) {
            if (firstItemSize > 0) {
                if (i == 0) {
                    //the first one is bigger than others
                    left = top = 0;
                    right = bottom = firstItemSize;
                } else {
                    if (i <= rightTopPartColumn * multiple) {
                        //right-top area
                        left = firstItemSize + mItemGap + (normalItemSize + mItemGap) * ((i - 1) % rightTopPartColumn);
                        top = (normalItemSize + mItemGap) * ((i - 1) / rightTopPartColumn);
                    } else {
                        //bottom area
                        int subIndex = i - rightTopPartColumn * multiple - 1;
                        left = (normalItemSize + mItemGap) * (subIndex % column);
                        top = firstItemSize + mItemGap + (normalItemSize + mItemGap) * (subIndex / column);
                    }
                    right = left + normalItemSize;
                    bottom = top + normalItemSize;
                }
            } else {
                if (i < firstLineNumb) {
                    int itemHeight = firtLineItemWidth;

                    //if first line has more than one item and total item more than one
                    if (firstLineNumb == 1 && count > 1) {
                        itemHeight = firtLineItemWidth / SIZE_RATIO; //or itemWidth / (colunm - 1)
                    }

                    //save height of first line item.
                    firstLineHeight = itemHeight;

                    left = (firtLineItemWidth + mItemGap) * i;
                    top = 0;
                    right = left + firtLineItemWidth;
                    bottom = top + itemHeight;
                } else {
                    left = (normalItemSize + mItemGap) * ((i - firstLineNumb) % column);
                    top = firstLineHeight + (firstLineHeight > 0 ? mItemGap : 0) +
                            (i - firstLineNumb) / column * (normalItemSize + mItemGap);
                    right = left + normalItemSize;
                    bottom = top + normalItemSize;
                }
            }

            mItemInfo[i][0] = left;
            mItemInfo[i][1] = top;
            mItemInfo[i][2] = right;
            mItemInfo[i][3] = bottom;
        }

        return mItemInfo[count - 1][3];
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count <= 0) {
            return;
        }

        for (int i = 0; i < count; i++) {
            getChildAt(i).layout(mItemInfo[i][0], mItemInfo[i][1], mItemInfo[i][2], mItemInfo[i][3]);
        }
    }

    public void setImageSources(Object[] urls) {
        for (Object url : urls) {
            addImageView((String) url);
        }
    }

    public void setImageSources(String[] urls) {
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
