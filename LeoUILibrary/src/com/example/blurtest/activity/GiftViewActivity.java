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
import com.example.blurtest.giftview.GratuityEntity;
import com.example.blurtest.giftview.GratuityView;
import com.example.blurtest.util.Player;
import com.squareup.okhttp.HttpUrl;

import java.net.MulticastSocket;
import java.util.Random;

public class GiftViewActivity extends Activity implements View.OnClickListener {

    private GratuityView mGratuityView;
    private Button mBtnAddSameItem;
    private Button mBtnAddRandomItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        mGratuityView = (GratuityView) findViewById(R.id.gratuity_item_container);
        mBtnAddSameItem = (Button) findViewById(R.id.btn_add_same_item);
        mBtnAddRandomItem = (Button) findViewById(R.id.btn_add_random_item);
        mBtnAddSameItem.setOnClickListener(this);
        mBtnAddRandomItem.setOnClickListener(this);
    }

    int count;

    String url = "http://e.hiphotos.baidu.com/zhidao/pic/item/83025aafa40f4bfb0f015264064f78f0f7361829.jpg";
    String imageUrl = "http://img3.duitang.com/uploads/blog/201411/22/20141122195215_TvSRS.thumb.700_0.png";
    String[] heads = {"http://www.veerchina.com/images/front/v//pic2/42/63/tukuchina_1103031918.jpg",
    "http://pic26.nipic.com/20130127/9391931_094607395166_2.jpg",
    "http://www.veerchina.com/images/front/v/pic2/42/5b/2175628231.jpg",
    "http://file.veerchina.com/images/front/v/6b/5a/tukuchina_220057.jpg",
    "http://img3.imgtn.bdimg.com/it/u=1701607579,1719955352&fm=21&gp=0.jpg",
    "http://img1.imgtn.bdimg.com/it/u=3598720979,2086257408&fm=21&gp=0.jpg",
    "http://img4.imgtn.bdimg.com/it/u=751607304,3722527436&fm=21&gp=0.jpg",
    "http://img1.imgtn.bdimg.com/it/u=1698911171,1960187042&fm=21&gp=0.jpg",
    "http://img2.imgtn.bdimg.com/it/u=3123024133,3387504173&fm=21&gp=0.jpg",
    "http://img0.imgtn.bdimg.com/it/u=2757094640,2942779802&fm=15&gp=0.jpg",
    "http://img1.imgtn.bdimg.com/it/u=2748204744,3707859395&fm=21&gp=0.jpg"};

    String[] gifts = {"http://www.veerchina.com/images/front/v//pic2/42/63/tukuchina_1103031918.jpg",
            "http://pic26.nipic.com/20130127/9391931_094607395166_2.jpg",
            "http://www.veerchina.com/images/front/v/pic2/42/5b/2175628231.jpg",
            "http://file.veerchina.com/images/front/v/6b/5a/tukuchina_220057.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1701607579,1719955352&fm=21&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3598720979,2086257408&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=751607304,3722527436&fm=21&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1698911171,1960187042&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3123024133,3387504173&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2757094640,2942779802&fm=15&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2748204744,3707859395&fm=21&gp=0.jpg"};


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_add_same_item) {
            addSameItem();
        } else if (id == R.id.btn_add_random_item) {
            setmBtnAddRandomItem();
        }
    }

    private void addSameItem() {
        count++;
        GratuityEntity entity = new GratuityEntity();
        entity.setHeadUrl(url);
        entity.setNickname("蒋扬海");
        entity.setGiftImgUrl(imageUrl);
        entity.setDescription("发送了一个礼物");
        entity.setShowTime(5);
        entity.setType("type1");
        entity.setUid("1");
        entity.setCount(String.valueOf(count));
        mGratuityView.addGratuityEntity(entity);
    }

    int[] typeCount = new int[11];
    String[] names = {"AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "GGG", "HHH", "XXX", "YYY", "ZZZ"};

    private void setmBtnAddRandomItem() {
        Random random1 = new Random();
        int type = random1.nextInt(11);
        typeCount[type]++;
        GratuityEntity entity = new GratuityEntity();
        entity.setHeadUrl(heads[type]);
        entity.setNickname(names[type]);
        entity.setDescription("发送了一个" + type + "礼物");
        entity.setShowTime(5);
        entity.setGiftImgUrl(gifts[type]);
        entity.setType("type" + type);
        entity.setUid(names[type]);
        entity.setCount("" + typeCount[type]);
        mGratuityView.addGratuityEntity(entity);
    }


}
