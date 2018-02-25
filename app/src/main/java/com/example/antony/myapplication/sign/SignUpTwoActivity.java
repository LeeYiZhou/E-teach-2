package com.example.antony.myapplication.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

public class SignUpTwoActivity extends BaseActivity implements SignUpTwoView {

    //各个控件对象，例如：private TextView accountName
    private EditText username;
    private EditText passwordOne;
    private EditText passwordTwo;
    private Button signUp;
    private SignUpTwoPresenter signUpTwoPresenter;
    private String phone;//手机号

    //自定义函数
    private void initViews(){//初始化各个控件

        username=(EditText)findViewById(R.id.username);
        passwordOne=(EditText)findViewById(R.id.password_one);
        passwordTwo=(EditText)findViewById(R.id.password_two);
        signUp=(Button)findViewById(R.id.sign_up);

    }

    private void initListener(){//初始化各个事件

        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(ActivityCollector.flag1==1)
                    signUpTwoPresenter.signUpPresenter();
                else
                    SignUpTwoActivity.this.showToastView("正在注册，请耐心等待");
            }
        });

    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_two);
        signUpTwoPresenter=new SignUpTwoPresenter(SignUpTwoActivity.this,SignUpTwoActivity.this);
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
        ActivityCollector.flag1=1;
        initViews();
        initListener();
    }

    //重写SignUpTwoView中的方法
    @Override
    public void showToastView(String str) {//显示Toast
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String showPhoneView() {//获取手机号
        return phone;
    }

    @Override
    public String showPasswordOneView() {//获得密码
        String password=passwordOne.getText().toString();
        if(TextUtils.isEmpty(password))
            return null;
        else
            return password;
    }

    @Override
    public String showPasswordTwoView() {//获得确认密码
        String password=passwordTwo.getText().toString();
        if(TextUtils.isEmpty(password))
            return null;
        else
            return password;
    }

    @Override
    public String showUsernameView(){//获取用户名
        String name=username.getText().toString();
        if(TextUtils.isEmpty(name))
            return null;
        else
            return name;
    }

    @Override
    public void startActivityView() {//打开活动
        Intent intent=new Intent(SignUpTwoActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
