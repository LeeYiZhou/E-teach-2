package com.example.antony.myapplication.sign;

import android.content.Context;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.data.MyInfo;

/**
 * Created by antony on 2018/2/21.
 */

public class SignUpTwoPresenter {
    private SignUpTwoView signUpTwoView;
    private ModelSignSource modelSignSource;
    private Context context;

    SignUpTwoPresenter(Context context,SignUpTwoView signUpTwoView){

        this.context=context;
        this.signUpTwoView=signUpTwoView;
        modelSignSource=new ModelSignSource();

    }

    public void signUpPresenter() {//手机号和密码注册

        ActivityCollector.flag1=0;
        String phone=signUpTwoView.showPhoneView();
        String username=signUpTwoView.showUsernameView();
        String passwordOne=signUpTwoView.showPasswordOneView();
        String passwordTwo=signUpTwoView.showPasswordTwoView();

        if(username==null) {
            signUpTwoView.showToastView("昵称不能为空");
            ActivityCollector.flag1=1;
        }
        else if(passwordOne==null||passwordTwo==null) {
            signUpTwoView.showToastView("密码不能为空");
            ActivityCollector.flag1=1;
        }
        else if(!passwordOne.equals(passwordTwo)){
            signUpTwoView.showToastView("请确保两次密码一致");
            ActivityCollector.flag1=1;
        }
        else{
            modelSignSource.signUpModel(context, phone, username, passwordOne, new PresenterSign() {
                @Override
                public void onSuccess() {
                    signUpTwoView.showToastView("注册成功");
                    signUpTwoView.startActivityView();
                    ActivityCollector.flag1=1;
                }

                @Override
                public void onFailure() {
                    signUpTwoView.showToastView("注册失败");
                    ActivityCollector.flag1=1;
                }
            });
        }

    }
}
