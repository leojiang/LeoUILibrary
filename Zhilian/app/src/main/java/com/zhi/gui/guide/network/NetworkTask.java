package com.zhi.gui.guide.network;

import org.json.JSONObject;

public class NetworkTask {


    private String url;
    private JSONObject json;
    private NetRequestListener mRequestListener;

    public NetworkTask(String url, JSONObject json) {
        this.url = url;
        this.json = json;
    }

    public void setNetRequestListener(NetRequestListener requestListener) {
        mRequestListener = requestListener;
    }


    public void execute() {
        NetworkRequest.post(url, json, mRequestListener);
    }
}
