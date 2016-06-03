package com.example.blurtest.aidl;

import com.example.blurtest.IAIDLTest;

import android.os.RemoteException;
import android.util.Log;

/**
 * Created by admin on 2016/5/27.
 */
public class AIDLTest extends IAIDLTest.Stub {

    @Override
    public void sayHello() throws RemoteException {
        Log.i("leojiang", "hello!");
    }
}
