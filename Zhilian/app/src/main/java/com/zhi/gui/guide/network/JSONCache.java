package com.zhi.gui.guide.network;

import java.util.Calendar;
import java.util.Date;


/**
 * 功能:对  DefaultJSONData 对进包装,可以保存 DefaultJSONData 缓存的时间 ,调用者可以根据时间来判断来是否使用此缓存
 * 备注:
 */
public class JSONCache<T> {

    private Date dtNow;

    private T mData;

    public JSONCache(T data) {
        this.dtNow = Calendar.getInstance().getTime();
        this.mData = data;
    }


    public Date getDate() {
        return this.dtNow;
    }

    public void setDate(Date dt) {
        this.dtNow = dt;
    }

    public T getJSONData() {
        return this.mData;
    }

    public void setJSONData(T data) {
        this.mData = data;
    }


}
