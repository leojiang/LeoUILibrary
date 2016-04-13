package com.example.blurtest.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.blurtest.R;
import com.example.blurtest.util.Player;
import com.squareup.okhttp.HttpUrl;

public class MediaplayerActivity extends Activity {
    private SurfaceView surfaceView;
    private Button btnPause, btnPlayUrl, btnStop;
    private SeekBar skbProgress;
    private Player player;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_mediaplayer);
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);

        btnPlayUrl = (Button) this.findViewById(R.id.btnPlayUrl);
        btnPlayUrl.setOnClickListener(new ClickEvent());

        btnPause = (Button) this.findViewById(R.id.btnPause);
        btnPause.setOnClickListener(new ClickEvent());

        btnStop = (Button) this.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new ClickEvent());

        skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        player = new Player(surfaceView, skbProgress);

    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            if (arg0 == btnPause) {
                player.pause();
            } else if (arg0 == btnPlayUrl) {
                String url="http://daily3gp.com/vids/family_guy_penis_car.3gp";
                //        http://123.150.52.227/0725695b00000000-1415769042-1960016430/data5/vkplx.video.qq.com/flv/74/164/a0015193bxf.p203.1.mp4
//        http://120.41.1.158/vkplx.video.qq.com/flv/74/164/a0015193bxf.p203.1.mp4
//        http://vkplx.video.qq.com/flv/74/164/a0015193bxf.p203.1.mp4
//        http://221.204.220.17//data5/cdn_transfer/36/4C/364f19b5662e054057fc9851703a03f3b0f4ea4c.flv
//        http://106.38.249.144/youku/67739CD2F943E7AB77EFF6511/03000810005429C7E703C905CF07DD613756C8-634B-EB1E-9628-F2F38A38C261.mp4
//                String url = "http://www.tudou.com/albumplay/Lqfme5hSolm/LhRbTdxhxtY.html";
//                String videoUri = "rtsp://192.168.1.100:554/video/3gpp";
                player.playUrl("http://106.38.249.144/youku/67739CD2F943E7AB77EFF6511/03000810005429C7E703C905CF07DD613756C8-634B-EB1E-9628-F2F38A38C261.mp4");
            } else if (arg0 == btnStop) {
                player.stop();
            }

        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }

}
