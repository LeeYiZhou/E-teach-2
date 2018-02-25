package com.example.antony.myapplication.sign;

import android.content.Context;

import com.example.antony.myapplication.ActivityCollector;

/**
 * Created by antony on 2018/2/20.
 */

public class SignInPresenter {
    private ModelSignSource modelSignSource;
    private SignInView signInView;
    private Context context;

    SignInPresenter(Context context,SignInView signInView){
        this.signInView=signInView;
        modelSignSource =new ModelSignSource();
        this.context=context;
    }

    public void signInByPhonePresenter() {//手机号和密码登陆

        ActivityCollector.flag1=0;
        String phone=signInView.showPhoneView();
        final String password=signInView.showPasswordView();
        if(phone==null||password==null){
            ActivityCollector.flag1=1;
            signInView.showToastView("输入不符合要求");
        }
        else
            modelSignSource.signInByPhoneModel(context, phone, new PresenterSign() {
                @Override
                public void onSuccess() {
                    ActivityCollector.flag1=1;
                    if(ActivityCollector.myInfo.getPassword().equals(password)){
                        signInView.showToastView("登陆成功");
                        signInView.startActivityView();
                    }
                    else
                        signInView.showToastView("密码不正确");
                }

                @Override
                public void onFailure() {
                    ActivityCollector.flag1=1;
                    if(ActivityCollector.flag2==0)
                        signInView.showToastView("登陆失败");
                    else if(ActivityCollector.flag2==1)
                        signInView.showToastView("该手机号还没注册");

                }
            });

    }

    public void signInByQQPresenter() {//QQ登陆
    }

    public void signInByWeiboPresenter() {//微博登陆
    }

    public void signInByWeixinPresenter() {//微信登陆
    }

    public boolean checkValidPresenter(){//检查手机号和密码是否有效
        return true;
    }
}
