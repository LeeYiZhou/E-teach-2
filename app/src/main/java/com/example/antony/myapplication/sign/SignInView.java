package com.example.antony.myapplication.sign;

/**
 * Created by antony on 2018/2/20.
 */

public interface SignInView {
    void showToastView(String str);//显示Toast
    String showPhoneView();//获得手机号
    String showPasswordView();//获得密码
    void startActivityView();//跳转到注册界面
}
