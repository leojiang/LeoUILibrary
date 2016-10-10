package com.zhi.gui.guide.network;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zhi.gui.guide.common.LogUtil;
import com.zhi.gui.guide.data.JSONEntityBase;

import org.json.JSONObject;

public class FastJsonCommonHandler<T extends JSONEntityBase> {
    final String TAG = FastJsonCommonHandler.class.getSimpleName();

    Class<T> mClass;
    T mData;

    public FastJsonCommonHandler(Class<T> tClass) {
        super();
        mClass = tClass;
    }

    public T getData() {
        return mData;
    }

    public void parse(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        LogUtil.d(TAG, "content:" + content);

        mData = JSON.parseObject(content, mClass);
        if (mData != null) {
            LogUtil.d(TAG, "JSON:" + JSON.toJSONString(mData));
            mData.onParsed();
        }
    }
}
