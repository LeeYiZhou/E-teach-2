package com.example.antony.myapplication.homepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

public class BindActivity extends BaseActivity implements BindView{

    //各个控件对象，例如：private TextView accountName
    private LinearLayout qq;
    private LinearLayout weixin;
    private LinearLayout weibo;
    private LinearLayout face;

    //自定义函数
    private void initViews(){//初始化各个控件
        qq=(LinearLayout)findViewById(R.id.qq);
        weixin=(LinearLayout)findViewById(R.id.weixin);
        weibo=(LinearLayout)findViewById(R.id.weibo);
        face=(LinearLayout)findViewById(R.id.face);
    }

    private void initListener(){//初始化各个事件

    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        initViews();
    }

    //重写BindView中的方法
}
