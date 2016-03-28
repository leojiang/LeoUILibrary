package com.example.blurtest.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.blurtest.R;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class ClassLoaderActivity extends Activity implements View.OnClickListener {
    private Button mButton;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classloader_test);

        mButton = (Button) findViewById(R.id.inject_button);
        mButton.setOnClickListener(this);
        mText = (TextView) findViewById(R.id.text);


    }

    @Override
    public void onClick(View v) {
        inject("leojiang");
    }

    public String inject(String libPath) {
        boolean hasBaseDexClassLoader = true;
        try {
            Class.forName("dalvik.system.BaseDexClassLoader");
        } catch (ClassNotFoundException e) {
            hasBaseDexClassLoader = false;
        }

        if (hasBaseDexClassLoader) {
            try {
                Application application = getApplication();
                PathClassLoader pathClassLoader = (PathClassLoader) application.getClassLoader();

                DexClassLoader dexClassLoader = new DexClassLoader(libPath, application.getDir("dex", 0).getAbsolutePath(), libPath, application.getClassLoader());
                Class classInstance = DexClassLoader.class.getSuperclass();
                Field field = classInstance.getDeclaredField("pathList");
                field.setAccessible(true);
                mText.setText(classInstance.getName() + "---" + field.getName() + " : " + field.get(pathClassLoader) + "\n\n");

                Object pathList = field.get(pathClassLoader);
                Class pathListClass = pathList.getClass();

                Field elementsField = pathListClass.getDeclaredField("dexElements");
                elementsField.setAccessible(true);
//                elementsField.set(pathList, null);
                mText.append(pathListClass.getName() + "---" + elementsField.getName() + " : " + elementsField.get(pathList));

//                Object dexElements = elementsField.get(pathList);
//                Class elementsListClass = dexElements.getClass();


                return "SUCCESS";

            } catch (Throwable e) {

                e.printStackTrace();
                mText.setText(e.toString());

                return android.util.Log.getStackTraceString(e);

            }

        } else {
            mText.setText("hasBaseDexClassLoader == false");
        }

        return "SUCCESS";

    }
}
