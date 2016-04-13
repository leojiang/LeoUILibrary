package com.example.blurtest.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.blurtest.R;
import com.example.blurtest.view.DownloadProgressBar;

public class FancyDownloadProgressBar extends Activity {

    private int val = 0;
    private View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_progressbar);
        background = findViewById(R.id.background);

        final DownloadProgressBar downloadProgressView = (DownloadProgressBar) findViewById(R.id.dpv3);
        final TextView successTextView = (TextView) findViewById(R.id.success_text_view);
        successTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val = val + 10;
                downloadProgressView.setProgress(val);
            }
        });
        Typeface robotoFont = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        successTextView.setTypeface(robotoFont);

        downloadProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() % 2 == 0) {
                    background.setBackgroundColor(getResources().getColor(R.color.red_primary_color));
                    downloadProgressView.playToError();
                } else {
                    background.setBackgroundColor(getResources().getColor(R.color.blue_primary_color));
                    downloadProgressView.playToSuccess();
                }
//                downloadProgressView.playManualProgressAnimation();
            }
        });
        downloadProgressView.setOnProgressUpdateListener(new DownloadProgressBar.OnProgressUpdateListener() {
            @Override
            public void onProgressUpdate(float currentPlayTime) {

            }

            @Override
            public void onAnimationStarted() {
                downloadProgressView.setEnabled(false);
            }

            @Override
            public void onAnimationEnded() {
                val = 0;
                successTextView.setText("Click to download");
                downloadProgressView.setEnabled(true);
            }

            @Override
            public void onAnimationSuccess() {
                successTextView.setText("Downloaded!");
            }

            @Override
            public void onAnimationError() {
                successTextView.setText("Aborted!");
            }

            @Override
            public void onManualProgressStarted() {

            }

            @Override
            public void onManualProgressEnded() {

            }
        });
    }
}
