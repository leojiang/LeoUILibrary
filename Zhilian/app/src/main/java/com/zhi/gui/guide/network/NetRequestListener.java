package com.zhi.gui.guide.network;

import org.json.JSONObject;

public interface NetRequestListener {
    public void onSucceed(String json);

    public void onFail(int code);

    public void onTimeout();

    public void onError(String reason);
}
