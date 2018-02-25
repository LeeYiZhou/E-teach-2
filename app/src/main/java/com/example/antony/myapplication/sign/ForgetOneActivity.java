package com.example.antony.myapplication.sign;

import android.os.Bundle;

import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

public class ForgetOneActivity extends BaseActivity implements ForgetOneView {

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
        setContentView(R.layout.activity_forget_one);
    }

    //重写SignUpVerificationView中的方法
}
