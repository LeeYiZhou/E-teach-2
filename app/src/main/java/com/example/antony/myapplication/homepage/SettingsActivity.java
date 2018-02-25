package com.example.antony.myapplication.homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.sign.SignInActivity;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    //各个控件对象，例如：private TextView accountName
    private LinearLayout personInfo;
    private LinearLayout bind;
    private LinearLayout logout;

    //自定义函数
    private void initViews(){//初始化各个控件
        personInfo=(LinearLayout)findViewById(R.id.person_info);
        bind=(LinearLayout)findViewById(R.id.bind);
        logout=(LinearLayout)findViewById(R.id.logout);
    }

    private void initListener(){//初始化各个事件
        personInfo.setOnClickListener(this);
        bind.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        initListener();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.person_info:
                Intent intent=new Intent(SettingsActivity.this,PersonInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.bind:
                Intent intent1=new Intent(SettingsActivity.this,BindActivity.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                ActivityCollector.finishAll();
                Intent intent2=new Intent(SettingsActivity.this, SignInActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
