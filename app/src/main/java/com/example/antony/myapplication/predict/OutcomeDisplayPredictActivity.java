package com.example.antony.myapplication.predict;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.data.Image;
import com.example.antony.myapplication.util.ImageLayout;
import com.example.antony.myapplication.util.RoundImageView;

public class OutcomeDisplayPredictActivity extends BaseActivity {

    /*控件对象*/
    private RoundImageView roundImageView;

    /*数据对象*/
    private Intent intent;
    private Image image;
    private String type;


    /*初始化控件*/

    /**
     * 初始化控件
     */
    private void initView(){
       roundImageView=(RoundImageView)findViewById(R.id.image);
    }
    /*初始化控件*/


    /*重写BaseActivity中的方法*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome_display_predict);
        initView();
        intent=getIntent();
        type=intent.getStringExtra("type");
        if(type.equals("online")){
            image=(Image)intent.getSerializableExtra("image");
            Glide.with(this).load(image.getUrl()).into(roundImageView);
        }

    }
     /*重写BaseActivity中的方法*/
}
