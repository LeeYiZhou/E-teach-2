package com.example.antony.myapplication.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

import cn.bmob.v3.Bmob;

public class SignInActivity extends BaseActivity implements SignInView {

    //各个控件对象，例如：private TextView accountName
    private EditText phone;//手机号
    private EditText password;//密码
    private TextView forgetPassword;//忘记密码
    private TextView signInByFace;//人脸识别登陆
    private TextView signUp;//注册
    private Button  signIn;//登陆按钮
    private ImageView signInByQQ;//qq登陆按钮
    private ImageView signInByWeixin;//微信登陆按钮
    private ImageView signInByWeibo;//微博登陆按钮
    private SignInPresenter signInPresenter;

    //自定义函数
    private void initViews(){//初始化各个控件

        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        forgetPassword=(TextView)findViewById(R.id.txt_forget_password);
        signInByFace=(TextView)findViewById(R.id.txt_face_recog);
        signUp=(TextView)findViewById(R.id.sign_up);
        signIn=(Button)findViewById(R.id.sign_in);
        signInByQQ=(ImageView)findViewById(R.id.img_qq);
        signInByWeixin=(ImageView)findViewById(R.id.weixin);
        signInByWeibo=(ImageView)findViewById(R.id.weibo);

    }

    private void initListener(){//初始化各个事件

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCollector.flag1==1)
                    signInPresenter.signInByPhonePresenter();
                else
                    SignInActivity.this.showToastView("正在登陆，请耐心等待");
            }
        });

        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(SignInActivity.this,SignUpOneActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ActivityCollector.flag1=1;
        signInPresenter=new SignInPresenter(SignInActivity.this,SignInActivity.this);
        initViews();
        initListener();
    }

    //重写SignInView中的方法
    @Override
    public void showToastView(String str) {//显示Toast
        Toast.makeText(SignInActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String showPhoneView() {//获得手机号

        String number=phone.getText().toString();
        if(TextUtils.isEmpty(number)||number.length()!=11)
            return null;
        else
            return number;
    }

    @Override
    public String showPasswordView() {//获得密码

        String number=password.getText().toString();
        if(TextUtils.isEmpty(number))
            return null;
        return number;
    }

    @Override
    public void startActivityView() {//跳转到注册界面
        Intent intent=new Intent(SignInActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
