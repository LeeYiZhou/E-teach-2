package com.example.antony.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.antony.myapplication.util.UltimateBar;

import cn.bmob.v3.Bmob;

/**
 * Created by antony on 2018/2/20.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar=new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.background_color));
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
