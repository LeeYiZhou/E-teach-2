package com.example.antony.myapplication.sign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

public class ForgetThreeActivity extends BaseActivity implements ForgetThreeView{

    //各个控件对象，例如：private TextView accountName
    private EditText password;
    private TextView nextStep;
    private ForgetThreePresenter forgetThreePresenter;

    //自定义函数
    private void initViews(){//初始化各个控件
        password=(EditText)findViewById(R.id.password);
        nextStep=(TextView)findViewById(R.id.nextStep);
    }

    private void initListener(){//初始化各个事件
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCollector.flag1==1)
                    forgetThreePresenter.updatePasswordPresenter();
                else
                    ForgetThreeActivity.this.showToastView("正在修改，请耐心等待");
            }
        });
    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_three);
        initViews();
        initListener();
        forgetThreePresenter=new ForgetThreePresenter(ForgetThreeActivity.this,ForgetThreeActivity.this);
        ActivityCollector.flag1=1;
    }

    //重写ForgetThreeView中的方法
    @Override
    public void showToastView(String str)
    {
        Toast.makeText(ForgetThreeActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String showPasswordView() {//获取密码
        String num=password.getText().toString();
        if(TextUtils.isEmpty(num))
            return null;
        return num;
    }

    @Override
    public void startActivityView() {//开启活动
        Intent intent=new Intent(ForgetThreeActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
