package com.example.antony.myapplication.sign;

import android.content.Context;

import com.example.antony.myapplication.data.MyInfo;

/**
 * Created by antony on 2018/2/20.
 */

public interface ModelSign {
    void sendVerificationModel(Context context, String phone,PresenterSign presenterSign);//发送验证码
    void queryByPhoneModel(Context context,String phone,PresenterSign presenterSign);//根据手机号查询账户
    void checkVerificationModel(Context context,String phone,String verification,PresenterSign presenterSign);//检查验证码是否正确
    void signUpModel(Context context,String phone,String username,String password,PresenterSign presenterSign);//手机号和密码注册
    void signInByPhoneModel(Context context,String phone,PresenterSign presenterSign);//使用手机号和密码登陆
    void queryQQIdModel(Context context,String id,PresenterSign presenterSign);//根据QQ查询账户
    void queryByWeixinIdModel(Context context,String id,PresenterSign presenterSign);//根据微信查询账户
    void queryByWeiboIdModel(Context context,String id,PresenterSign presenterSign);//根据微博查询账户
}
