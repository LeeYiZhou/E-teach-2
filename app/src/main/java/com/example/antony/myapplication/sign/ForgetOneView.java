package com.example.antony.myapplication.sign;

/**
 * Created by antony on 2018/2/21.
 */

public interface ForgetOneView {
    void showToastView(String str);
    String showPhoneView();//获取手机号
    void startActivityView(String phone,String type);//开启活动
    String showTypeView();//判断手机验证码登陆、找回密码
}
