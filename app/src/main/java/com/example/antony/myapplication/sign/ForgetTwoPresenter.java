package com.example.antony.myapplication.sign;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.antony.myapplication.ActivityCollector;

/**
 * Created by antony on 2018/2/23.
 */

public class ForgetTwoPresenter {

    private ForgetTwoView forgetTwoView;
    private ModelSignSource modelSignSource;
    private Context context;
    private CountDownTimer timer = new CountDownTimer(60000, 1000){
        //CountDownTimer timer = new CountDownTimer(10000, 1000)中，
        //第一个参数表示总时间，第二个参数表示间隔时间。意思就是每隔一秒会回调一次方法onTick，然后10秒之后会回调onFinish方法。
        @Override
        public void onTick(long millisUntilFinished) {
            forgetTwoView.showSendView((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            forgetTwoView.showSendView("重新发送");
            ActivityCollector.flag1=1;
        }
    };

    ForgetTwoPresenter(Context context,ForgetTwoView forgetTwoView){
        this.context=context;
        this.forgetTwoView=forgetTwoView;
        modelSignSource=new ModelSignSource();
    }

    public void startTimerPresenter(){
        ActivityCollector.flag1=0;
        timer.start();
    }

    public void checkVerificationPresenter() {//检查验证码是否正确
        String phone=ActivityCollector.myInfo.getPhone();
        String verification=forgetTwoView.showVerificationView();
        modelSignSource.checkVerificationModel(context,phone, verification, new PresenterSign() {
            @Override
            public void onSuccess() {
                forgetTwoView.showToastView("验证成功");
                forgetTwoView.startActivityView();
            }

            @Override
            public void onFailure() {
                forgetTwoView.showToastView("验证失败");
            }
        });
    }

    public void sendVerificationPresenter() {//发送验证码
        ActivityCollector.flag1=0;
        String phone=ActivityCollector.myInfo.getPhone();
        modelSignSource.sendVerificationModel(context, phone, new PresenterSign() {
            @Override
            public void onSuccess() {
                forgetTwoView.showToastView("发送成功");
                timer.start();
            }

            @Override
            public void onFailure() {
                ActivityCollector.flag1=1;
                forgetTwoView.showToastView("发送失败");
                forgetTwoView.showSendView("重新发送");
            }
        });
    }

}
