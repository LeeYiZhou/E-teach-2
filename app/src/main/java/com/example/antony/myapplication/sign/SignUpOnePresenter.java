package com.example.antony.myapplication.sign;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import com.example.antony.myapplication.ActivityCollector;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by antony on 2018/2/21.
 */

public class SignUpOnePresenter{
    private SignUpOneView signUpOneView;
    private ModelSignSource modelSignSource;
    private Context context;

    private CountDownTimer timer = new CountDownTimer(60000, 1000){
        //CountDownTimer timer = new CountDownTimer(10000, 1000)中，
        //第一个参数表示总时间，第二个参数表示间隔时间。意思就是每隔一秒会回调一次方法onTick，然后10秒之后会回调onFinish方法。
        @Override
        public void onTick(long millisUntilFinished) {
            signUpOneView.showSendView((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {

            signUpOneView.showSendView("发送");
            ActivityCollector.flag1=1;
        }
    };

    SignUpOnePresenter(Context context,SignUpOneView signUpOneView){
        this.context=context;
        this.signUpOneView = signUpOneView;
        modelSignSource =new ModelSignSource();
    }

    private void sendVerification(String phone){//只发送验证码
        modelSignSource.sendVerificationModel(context, phone, new PresenterSign() {//回调接口
            @Override
            public void onSuccess() {
                signUpOneView.showToastView("验证码发送成功");
                timer.start();
            }

            @Override
            public void onFailure() {

                signUpOneView.showToastView("验证码发送失败");
                ActivityCollector.flag1=1;

            }
        });
    }

    public void sendVerificationPresenter() {//发送验证码

        ActivityCollector.flag1=0;
        final String phone=signUpOneView.showPhoneView();
        if(phone==null){
            signUpOneView.showToastView("手机号输入错误");
            ActivityCollector.flag1=1;
        }
        else{
            modelSignSource.queryByPhoneModel(context, phone, new PresenterSign() {
                @Override
                public void onSuccess() {
                    sendVerification(phone);
                }

                @Override
                public void onFailure() {
                    ActivityCollector.flag1=1;
                    if(ActivityCollector.flag2==0)
                        signUpOneView.showToastView("验证码发送失败");
                    else if(ActivityCollector.flag2==1)
                        signUpOneView.showToastView("该手机号已经被注册");
                }
            });
        }

    }

    public void signInPresenter() {//登陆
        ActivityCollector.flag2=0;
        String verification=signUpOneView.showVerificationView();
        final String phone=signUpOneView.showPhoneView();
        if(verification==null||phone==null)
        {
            ActivityCollector.flag2=1;
            signUpOneView.showToastView("手机号或验证码输入错误");
        }
        else{
            modelSignSource.checkVerificationModel(context, phone, verification, new PresenterSign() {
                @Override
                public void onSuccess() {
                    ActivityCollector.flag2=1;
                    signUpOneView.showToastView("验证成功");
                    signUpOneView.startActivityView(phone);
                }

                @Override
                public void onFailure() {
                    ActivityCollector.flag2=1;
                    signUpOneView.showToastView("验证失败");
                }
            });
        }
    }

}
