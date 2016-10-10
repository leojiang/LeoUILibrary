package com.zhi.gui.guide.network;

import android.os.Environment;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能:可以查询及存储HTTP请求和回应 . 根据http url 及 请求参数做为 hash key ,回应的 response 回应时间
 * 做为 hash value . 备注:处理每次都要从服务器获取JSON数据导致android 应用界面每次都重新刷新问题
 */
public class MemCacheManager {

    private static MemCacheManager _instance = new MemCacheManager(5);
    private int mCapacity = 0;
    private Map<String, JSONCache<String>> mHttpCache = null;

    private MemCacheManager(int capacity) {
        mCapacity = capacity;
        mHttpCache = new ConcurrentHashMap<String, JSONCache<String>>(
                capacity);
    }

    public static MemCacheManager getInstance() {
        return _instance;
    }

    public void removeAllCache() {
        mHttpCache.clear();
    }

    public void RemoveDefaultJSONData(String strKey) {
        if (mHttpCache.containsKey(strKey)) {
            mHttpCache.remove(strKey);
        }
    }

    public void SaveDefaultJSONData(String strKey, String Info) {

        JSONCache<String> data = new JSONCache<String>(Info);
        if (mHttpCache.containsKey(strKey)) {
            mHttpCache.remove(strKey);
            mHttpCache.put(strKey, data);
        } else {
            mHttpCache.put(strKey, data);
        }
    }


    public String LoadDefaultJSONData(String strKey, long maxCacheTimeSecond) {
        String info;
        JSONCache<String> data = mHttpCache.get(strKey);
        if (null != data) {

            long nMillsecond = Calendar.getInstance().getTime().getTime() -
                    data.getDate().getTime();
            if ((nMillsecond / 1000) < maxCacheTimeSecond + 1) {
                info = data.getJSONData();
            } else {
                info = null;
            }
        } else {
            info = null;
        }
        return info;
    }


    public String GetHashKey(String path, Map<String, String> param, String strExtKey) {
        String strRet = "";
        if (null != path && null != param) {
            strRet = path + "|" + param.toString() + "|" + strExtKey;
        }
        return strRet;
    }


}
