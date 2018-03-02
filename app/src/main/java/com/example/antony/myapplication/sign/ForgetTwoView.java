package com.example.antony.myapplication.sign;

/**
 * Created by antony on 2018/2/23.
 */

public interface ForgetTwoView {
    void showToastView(String str);
    void startActivityView();//开启活动
    String showVerificationView();//获取验证码
    void showSendView(String str);//设置倒计时显示文字
}
