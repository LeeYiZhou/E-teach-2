package com.example.antony.myapplication.sign;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.util.SecurityCodeView;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class ForgetTwoActivity extends BaseActivity implements ForgetTwoView,SecurityCodeView.InputCompleteListener{

    //各个控件对象，例如：private TextView accountName
    private TextView phone;
    private TextView verification;
    private ForgetTwoPresenter forgetTwoPresenter;
    private SecurityCodeView securityCodeView;
    private String type;

    //自定义函数
    private void initViews(){//初始化各个控件
        phone=(TextView)findViewById(R.id.phone);
        verification=(TextView)findViewById(R.id.verification);
        securityCodeView=(SecurityCodeView)findViewById(R.id.input);
    }

    private void initListener(){//初始化各个事件
        securityCodeView.setInputCompleteListener(this);
        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCollector.flag1==1)
                    forgetTwoPresenter.sendVerificationPresenter();
            }
        });
    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_two);
        initViews();
        initListener();
        phone.setText(ActivityCollector.myInfo.getPhone());
        forgetTwoPresenter=new ForgetTwoPresenter(ForgetTwoActivity.this,ForgetTwoActivity.this);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        forgetTwoPresenter.startTimerPresenter();
    }

    //重写ForgetTwoView中的方法
    @Override
    public void showSendView(String str) {//设置倒计时显示文字
        verification.setText(str);
    }
    @Override
    public void showToastView(String str){
        Toast.makeText(ForgetTwoActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivityView() {//开启活动
        if(type.equals("find")){
            Intent intent=new Intent(ForgetTwoActivity.this,ForgetThreeActivity.class);
            startActivity(intent);
        }
        else if(type.equals("verification")){
            Intent intent=new Intent(ForgetTwoActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public String showVerificationView() {//获取是找回密码还是验证码登陆
        return securityCodeView.getEditContent();
    }

    //SecurityCodeView.InputCompleteListener
    @Override
    public void inputComplete() {
        forgetTwoPresenter.checkVerificationPresenter();
    }

    @Override
    public void deleteContent(boolean isDelete) {
        if (isDelete){

        }
    }
}
