package com.example.antony.myapplication.sign;

import android.widget.Button;

/**
 * Created by antony on 2018/2/21.
 */

public interface SignUpOneView {

    String showPhoneView();//获取手机号
    String showVerificationView();//获取验证码
    void showToastView(String str);
    void showSendView(String str);//设置发送按钮文字
    void startActivityView(String phone);//打开界面

}
