package com.example.antony.myapplication.sign;

import android.content.Context;

import com.example.antony.myapplication.ActivityCollector;

/**
 * Created by antony on 2018/2/21.
 */

public class ForgetOnePresenter {
    private ForgetOneView forgetOneView;
    private ModelSignSource modelSignSource;
    private Context context;

    ForgetOnePresenter(Context context,ForgetOneView forgetOneView){
        this.forgetOneView = forgetOneView;
        modelSignSource =new ModelSignSource();
        this.context=context;
    }

    private void sendVerification(String phone){//只发送验证码
        modelSignSource.sendVerificationModel(context, phone, new PresenterSign() {//回调接口
            @Override
            public void onSuccess() {
                forgetOneView.showToastView("验证码发送成功");
                forgetOneView.startActivityView(forgetOneView.showPhoneView(),forgetOneView.showTypeView());
                ActivityCollector.flag1=1;
            }

            @Override
            public void onFailure() {
                forgetOneView.showToastView("验证码发送失败");
                ActivityCollector.flag1=1;

            }
        });
    }

    public void sendVerificationPresenter(){//发送验证码

        ActivityCollector.flag1=0;
        final String phone=forgetOneView.showPhoneView();
        if(phone==null){
            forgetOneView.showToastView("手机号输入错误");
            ActivityCollector.flag1=1;
        }
        else{
            modelSignSource.queryByPhoneModel(context, phone, new PresenterSign() {
                @Override
                public void onSuccess() {
                    ActivityCollector.flag1=1;
                    forgetOneView.showToastView("该手机号还没有注册");
                }

                @Override
                public void onFailure() {
                    if(ActivityCollector.flag2==0)
                    {
                        ActivityCollector.flag1=1;
                        forgetOneView.showToastView("验证码发送失败");
                    }
                    else if(ActivityCollector.flag2==1){
                        sendVerification(phone);
                    }
                }
            });
        }

    }
}
