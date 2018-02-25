package com.example.antony.myapplication.sign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

public class ForgetTwoActivity extends BaseActivity implements ForgetTwoView{

    //各个控件对象，例如：private TextView accountName

    //自定义函数
    private void initViews(){//初始化各个控件

    }

    private void initListener(){//初始化各个事件

    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_two);
    }

    //重写ForgetTwoView中的方法
}
