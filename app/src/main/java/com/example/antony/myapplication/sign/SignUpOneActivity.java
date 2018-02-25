package com.example.antony.myapplication.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

public class SignUpOneActivity extends BaseActivity implements SignUpOneView {

    //各个控件对象，例如：private TextView accountName
    private EditText phone;//手机号
    private Button send;//发送验证码
    private EditText verification;//验证码
    private Button signIn;//登陆
    private SignUpOnePresenter signUpOnePresenter;

    //自定义函数
    private void initViews(){//初始化各个控件

        phone=(EditText)findViewById(R.id.phone);
        send=(Button)findViewById(R.id.send);
        verification=(EditText)findViewById(R.id.verification);
        signIn=(Button)findViewById(R.id.sign_in);

    }

    private void initListener(){//初始化各个事件

        final String phoneNumber=phone.getText().toString();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCollector.flag1==1)
                    signUpOnePresenter.sendVerificationPresenter();
                else
                    SignUpOneActivity.this.showToastView("正在发送，请耐心等待");
            }
        });
        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(ActivityCollector.flag2==1)
                    signUpOnePresenter.signInPresenter();
                else
                    SignUpOneActivity.this.showToastView("正在验证，请耐心等待");
            }
        });

    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_one);
        ActivityCollector.flag1=1;
        ActivityCollector.flag2=1;
        signUpOnePresenter=new SignUpOnePresenter(SignUpOneActivity.this,this);
        initViews();
        initListener();
    }

    //重写SignUpOneView中的方法
    @Override
    public String showPhoneView() {//获取手机号
        String str=phone.getText().toString();
        if(TextUtils.isEmpty(str)||str.length()!=11)
            return null;
        return str;
    }

    @Override
    public String showVerificationView() {//获取验证码
        String str=verification.getText().toString();
        if(TextUtils.isEmpty(str)||str.length()!=6)
            return null;
        return str;
    }

    @Override
    public void showToastView(String str){
        Toast.makeText(SignUpOneActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendView(String str) {//设置发送按钮文字
        send.setText(str);
    }

    @Override
    public void startActivityView(String phone){//打开界面

        Intent intent=new Intent(SignUpOneActivity.this,SignUpTwoActivity.class);
        intent.putExtra("phone",phone);
        startActivity(intent);

    }

}
