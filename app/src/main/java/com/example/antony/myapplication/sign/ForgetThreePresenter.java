package com.example.antony.myapplication.sign;

import android.content.Context;

import com.example.antony.myapplication.ActivityCollector;

/**
 * Created by antony on 2018/2/23.
 */

public class ForgetThreePresenter {
    private ForgetThreeView forgetThreeView;
    private ModelSignSource modelSignSource;
    private Context context;

    ForgetThreePresenter(Context context,ForgetThreeView forgetThreeView){
        this.forgetThreeView=forgetThreeView;
        this.context=context;
        modelSignSource=new ModelSignSource();
    }

    public void updatePasswordPresenter(){//更新密码
        ActivityCollector.flag1=0;
        String password=forgetThreeView.showPasswordView();
        if(password==null){
            ActivityCollector.flag1=1;
            forgetThreeView.showToastView("密码不能为空");
        }
        else {
            modelSignSource.updatePasswordModel(context, password, new PresenterSign() {
                @Override
                public void onSuccess() {
                    ActivityCollector.flag1=1;
                    forgetThreeView.showToastView("修改成功");
                    forgetThreeView.startActivityView();
                }

                @Override
                public void onFailure() {
                    ActivityCollector.flag1=1;
                    forgetThreeView.showToastView("修改失败");
                }
            });
        }
    }

}
