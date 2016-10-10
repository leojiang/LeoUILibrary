package com.zhi.gui.guide.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhi.gui.guide.R;

public class RefreshableView extends LinearLayout implements OnTouchListener, AbsListView.OnScrollListener {

    public static final int STATUS_PULL_TO_REFRESH = 0;
    public static final int STATUS_RELEASE_TO_REFRESH = 1;
    public static final int STATUS_REFRESHING = 2;
    public static final int STATUS_REFRESH_FINISHED = 3;
    public static final int SCROLL_SPEED = -20;
    public static final long ONE_MINUTE = 60 * 1000;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;
    public static final long ONE_MONTH = 30 * ONE_DAY;
    public static final long ONE_YEAR = 12 * ONE_MONTH;
    private static final String UPDATED_AT = "updated_at";
    private RefreshAndLoadListener mListener;
    private SharedPreferences preferences;
    private View header;
    private View footer;
    private ListView listView;
    private ProgressBar headerProgress;
    private ImageView headArrow;
    private TextView description;
    private TextView updateAt;
    private MarginLayoutParams headerLayoutParams;
    private long lastUpdateTime;
    private int mId = -1;
    private int hideHeaderHeight;
    private int currentStatus = STATUS_REFRESH_FINISHED;
    private int lastStatus = currentStatus;
    private float yDown;
    private int touchSlop;
    private boolean loadOnce;
    private boolean ableToPull;
    private ProgressBar footerProgress;
    private TextView footerTextView;

    public RefreshableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true);
        headerProgress = (ProgressBar) header.findViewById(R.id.progress_bar);
        headArrow = (ImageView) header.findViewById(R.id.arrow);
        description = (TextView) header.findViewById(R.id.description);
        updateAt = (TextView) header.findViewById(R.id.updated_at);
        footer = LayoutInflater.from(context).inflate(R.layout.list_footer, null, true);
        footerProgress = (ProgressBar) footer.findViewById(R.id.progress_bar);
        footerTextView = (TextView) footer.findViewById(R.id.description);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        refreshUpdatedAtValue();
        setOrientation(VERTICAL);
        addView(header, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce) {
            hideHeaderHeight = -header.getHeight();
            headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
            headerLayoutParams.topMargin = hideHeaderHeight;
            listView = (ListView) getChildAt(1);
            listView.addFooterView(footer);
            listView.setOnTouchListener(this);
            listView.setOnScrollListener(this);
            loadOnce = true;
            header.setLayoutParams(headerLayoutParams);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        setIsAbleToPull(event);

        if (!ableToPull) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float yMove = event.getRawY();
                int distance = (int) (yMove - yDown);
                // if header is hiding and finger is moving up, do nothing
                if (distance <= 0 && headerLayoutParams.topMargin <= hideHeaderHeight) {
                    return false;
                }
                if (distance < touchSlop) {
                    return false;
                }
                if (currentStatus == STATUS_REFRESHING) {
                    return false;
                }

                if (headerLayoutParams.topMargin > 0) {
                    currentStatus = STATUS_RELEASE_TO_REFRESH;
                } else {
                    currentStatus = STATUS_PULL_TO_REFRESH;
                }
                //adjust header margin size in order to show animation.
                headerLayoutParams.topMargin = (distance / 2) + hideHeaderHeight;
                header.setLayoutParams(headerLayoutParams);
                break;
            case MotionEvent.ACTION_UP:
            default:
                if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                    new RefreshingTask().execute();
                } else if (currentStatus == STATUS_PULL_TO_REFRESH) {
                    new HideHeaderTask().execute();
                }
                break;
        }

        if (currentStatus == STATUS_PULL_TO_REFRESH || currentStatus == STATUS_RELEASE_TO_REFRESH) {
            updateHeaderView();
            // remember to clear focus of ListView so that the highlighted item is reset to norma color.
            listView.setPressed(false);
            listView.setFocusable(false);
            listView.setFocusableInTouchMode(false);
            lastStatus = currentStatus;
            // return true to intercept touch event of ListView.
            return true;
        }
        return false;
    }

    private boolean isLoadingMore = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                if (listView.getLastVisiblePosition() == (listView.getCount() - 1) && !isLoadingMore) {
                    new LoadMoreTask().execute();
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void setOnRefreshListener(RefreshAndLoadListener listener, int id) {
        mListener = listener;
        mId = id;
    }

    private void finishRefreshing() {
        currentStatus = STATUS_REFRESH_FINISHED;
        preferences.edit().putLong(UPDATED_AT + mId, System.currentTimeMillis()).commit();
        new HideHeaderTask().execute();
    }

    private void setIsAbleToPull(MotionEvent event) {
        View firstChild = listView.getChildAt(0);
        if (firstChild != null) {
            int firstVisiblePos = listView.getFirstVisiblePosition();
            if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
                if (!ableToPull) {
                    yDown = event.getRawY();
                }
                ableToPull = true;
            } else {
                if (headerLayoutParams.topMargin != hideHeaderHeight) {
                    headerLayoutParams.topMargin = hideHeaderHeight;
                    header.setLayoutParams(headerLayoutParams);
                }
                ableToPull = false;
            }
        } else {
            //if there no item in the ListView, should be able to pull.
            ableToPull = true;
        }
    }

    private void updateFooterView() {
        if (isLoadingMore) {
            footerTextView.setVisibility(View.GONE);
            footerProgress.setVisibility(View.VISIBLE);
        } else {
            footerTextView.setVisibility(View.VISIBLE);
            footerProgress.setVisibility(View.GONE);
        }
    }

    private void updateHeaderView() {
        if (lastStatus == currentStatus)
            return;

        if (currentStatus == STATUS_PULL_TO_REFRESH) {
            description.setText(getResources().getString(R.string.pull_to_refresh));
            headArrow.setVisibility(View.VISIBLE);
            headerProgress.setVisibility(View.GONE);
            rotateArrow();
        } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
            description.setText(getResources().getString(R.string.release_to_refresh));
            headArrow.setVisibility(View.VISIBLE);
            headerProgress.setVisibility(View.GONE);
            rotateArrow();
        } else if (currentStatus == STATUS_REFRESHING) {
            description.setText(getResources().getString(R.string.refreshing));
            headerProgress.setVisibility(View.VISIBLE);
            headArrow.clearAnimation();
            headArrow.setVisibility(View.GONE);
        }
        refreshUpdatedAtValue();
    }

    private void rotateArrow() {
        float pivotX = headArrow.getWidth() / 2f;
        float pivotY = headArrow.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (currentStatus == STATUS_PULL_TO_REFRESH) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
            fromDegrees = 0f;
            toDegrees = 180f;
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        headArrow.startAnimation(animation);
    }

    private void refreshUpdatedAtValue() {
        lastUpdateTime = preferences.getLong(UPDATED_AT + mId, -1);
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastUpdateTime;
        long timeIntoFormat;
        String updateAtValue;
        if (lastUpdateTime == -1) {
            updateAtValue = getResources().getString(R.string.not_updated_yet);
        } else if (timePassed < 0) {
            updateAtValue = getResources().getString(R.string.time_error);
        } else if (timePassed < ONE_MINUTE) {
            updateAtValue = getResources().getString(R.string.updated_just_now);
        } else if (timePassed < ONE_HOUR) {
            timeIntoFormat = timePassed / ONE_MINUTE;
            String value = timeIntoFormat + "分钟";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_DAY) {
            timeIntoFormat = timePassed / ONE_HOUR;
            String value = timeIntoFormat + "小时";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_MONTH) {
            timeIntoFormat = timePassed / ONE_DAY;
            String value = timeIntoFormat + "天";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_YEAR) {
            timeIntoFormat = timePassed / ONE_MONTH;
            String value = timeIntoFormat + "个月";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else {
            timeIntoFormat = timePassed / ONE_YEAR;
            String value = timeIntoFormat + "年";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        }
        updateAt.setText(updateAtValue);
    }

    private class RefreshingTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int topMargin = headerLayoutParams.topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;
                if (topMargin <= 0) {
                    topMargin = 0;
                    break;
                }
                publishProgress(topMargin);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currentStatus = STATUS_REFRESHING;
            publishProgress(0);
            if (mListener != null) {
                mListener.onRefresh();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            updateHeaderView();
            headerLayoutParams.topMargin = topMargin[0];
            header.setLayoutParams(headerLayoutParams);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finishRefreshing();
        }
    }

    private class LoadMoreTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoadingMore = true;
            updateFooterView();
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (mListener != null) {
                mListener.onLoad();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isLoadingMore = false;
            updateFooterView();
        }
    }

    private class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            int topMargin = headerLayoutParams.topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;
                if (topMargin <= hideHeaderHeight) {
                    topMargin = hideHeaderHeight;
                    break;
                }
                publishProgress(topMargin);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return topMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            headerLayoutParams.topMargin = topMargin[0];
            header.setLayoutParams(headerLayoutParams);
        }

        @Override
        protected void onPostExecute(Integer topMargin) {
            headerLayoutParams.topMargin = topMargin;
            header.setLayoutParams(headerLayoutParams);
            currentStatus = STATUS_REFRESH_FINISHED;
        }
    }

    public interface RefreshAndLoadListener {
        void onRefresh();

        void onLoad();
    }

}
