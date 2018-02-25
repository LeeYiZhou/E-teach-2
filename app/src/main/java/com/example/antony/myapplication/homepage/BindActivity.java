package com.example.antony.myapplication.homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.tencent.tauth.Tencent;

public class BindActivity extends BaseActivity implements BindView,View.OnClickListener{

    //各个控件对象，例如：private TextView accountName
    private LinearLayout qq;
    private LinearLayout weixin;
    private LinearLayout weibo;
    private LinearLayout face;
    private BindPresenter bindPresenter;

    //自定义函数
    private void initViews(){//初始化各个控件
        qq=(LinearLayout)findViewById(R.id.qq);
        weixin=(LinearLayout)findViewById(R.id.weixin);
        weibo=(LinearLayout)findViewById(R.id.weibo);
        face=(LinearLayout)findViewById(R.id.face);
    }

    private void initListener(){//初始化各个事件
        qq.setOnClickListener(this);
        weixin.setOnClickListener(this);
        weibo.setOnClickListener(this);
        face.setOnClickListener(this);
    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        bindPresenter=new BindPresenter(BindActivity.this,BindActivity.this);
        initViews();
        initListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        bindPresenter.startActivityPresenter(requestCode,resultCode,data);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.qq:
                bindPresenter.bindQQPresenter();
                break;
            case R.id.weixin:
            case R.id.weibo:
            case R.id.face:
                BindActivity.this.showToastView("正在开发，敬请期待");
        }
    }

    //重写BindView中的方法
    @Override
    public void showToastView(String str){
        Toast.makeText(BindActivity.this,str,Toast.LENGTH_SHORT).show();
    }

}
