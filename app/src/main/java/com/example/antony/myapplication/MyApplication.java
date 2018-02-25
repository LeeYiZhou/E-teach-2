package com.example.antony.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by antony on 2018/2/20.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
