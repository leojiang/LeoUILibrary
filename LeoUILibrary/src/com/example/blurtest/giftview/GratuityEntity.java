package com.example.blurtest.giftview;

/**
 * Created by admin on 2016/2/20.
 */
public class GratuityEntity {
    private String nickname;
    private String headUrl;
    private String money;
    private String description;
    private long time;
    private String totalMoney;
    private int showTime;
    private String uid ;
    private String giftImgUrl;

    public String getGiftImgUrl() {
        return giftImgUrl;
    }

    public void setGiftImgUrl(String giftImgUrl) {
        this.giftImgUrl = giftImgUrl;
    }

    private String type;//礼物类型
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getShowTime() {
        return showTime;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
