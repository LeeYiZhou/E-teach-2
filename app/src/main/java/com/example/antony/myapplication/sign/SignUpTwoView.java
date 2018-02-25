package com.example.antony.myapplication.sign;

/**
 * Created by antony on 2018/2/21.
 */

public interface SignUpTwoView {
    void showToastView(String str);//显示Toast
    String showPhoneView();//获取手机号
    String showPasswordOneView();//获得密码
    String showPasswordTwoView();//获得确认密码
    String showUsernameView();//获取用户名
    void startActivityView();//打开活动
}
