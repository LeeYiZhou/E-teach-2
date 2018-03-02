package com.example.antony.myapplication.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

public class ForgetOneActivity extends BaseActivity implements ForgetOneView {

    //各个控件对象，例如：private TextView accountName
    private EditText phone;
    private TextView nextStep;
    private String type;
    private ForgetOnePresenter forgetOnePresenter;

    //自定义函数
    private void initViews(){//初始化各个控件
        phone=(EditText)findViewById(R.id.phone);
        nextStep=(TextView)findViewById(R.id.nextStep);
    }

    private void initListener(){//初始化各个事件
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCollector.flag1==1)
                    forgetOnePresenter.sendVerificationPresenter();
                else
                    ForgetOneActivity.this.showToastView("正在发送验证码，请耐心等待");
            }
        });
    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_one);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        forgetOnePresenter=new ForgetOnePresenter(ForgetOneActivity.this,ForgetOneActivity.this);
        ActivityCollector.flag1=1;
        initViews();
        initListener();
    }

    //重写ForgetOneView中的方法
    @Override
    public void showToastView(String str){
        Toast.makeText(ForgetOneActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String showPhoneView() {//获取手机号
        String str=phone.getText().toString();
        if(TextUtils.isEmpty(str)||str.length()!=11)
            return null;
        return str;
    }

    @Override
    public void startActivityView(String phone,String type) {//开启活动
        Intent intent=new Intent(ForgetOneActivity.this,ForgetTwoActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("phone",phone);
        startActivity(intent);
    }

    @Override
    public String showTypeView(){//判断手机验证码登陆、找回密码
        return type;
    }
}
