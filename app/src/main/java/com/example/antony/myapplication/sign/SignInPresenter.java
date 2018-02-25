package com.example.antony.myapplication.sign;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.homepage.BindActivity;
import com.example.antony.myapplication.homepage.BindPresenter;
import com.example.antony.myapplication.homepage.PresenterHomepage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;

/**
 * Created by antony on 2018/2/20.
 */

public class SignInPresenter {
    private ModelSignSource modelSignSource;
    private SignInView signInView;
    private SignInActivity signInActivity;
    private Tencent mTencent;
    private BaseUiListener listener;

    SignInPresenter(SignInActivity signInActivity,SignInView signInView){
        this.signInView=signInView;
        modelSignSource =new ModelSignSource();
        this.signInActivity=signInActivity;
        mTencent= Tencent.createInstance(ActivityCollector.QQID,signInActivity);
        listener = new BaseUiListener();
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
            modelSignSource.signInByPhoneModel(signInActivity, phone, new PresenterSign() {
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
        mTencent.login(signInActivity,"all",listener);
    }

    public void startActivityPresenter(int requestCode, int resultCode, Intent data){
        Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener());
    }

    public void signInByWeiboPresenter() {//微博登陆
    }

    public void signInByWeixinPresenter() {//微信登陆
    }

    public boolean checkValidPresenter(){//检查手机号和密码是否有效
        return true;
    }

    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {

            signInView.showToastView("开始授权");
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(o.toString());
                String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                final String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                        && !TextUtils.isEmpty(openId)) {
                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openId);

                    modelSignSource.queryQQIdModel(signInActivity, openId, new PresenterSign() {
                        @Override
                        public void onSuccess() {
                            signInView.startActivityView();
                        }

                        @Override
                        public void onFailure() {
                            if(ActivityCollector.flag2==0)
                                signInView.showToastView("授权出错");
                            else if(ActivityCollector.flag2==1)
                                signInView.showToastView("该QQ号还没有绑定");
                        }
                    });
                }
                else
                    signInView.showToastView("授权出错");

            } catch (JSONException e) {
                e.printStackTrace();
                signInView.showToastView("授权出错");
            }
        }

        @Override
        public void onError(UiError e) {
            signInView.showToastView("Error");
        }

        @Override
        public void onCancel() {
            signInView.showToastView("撤销授权");
        }
    }


}
