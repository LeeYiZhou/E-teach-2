package com.example.antony.myapplication.sign;

import android.content.Context;
import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.data.MyInfo;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by antony on 2018/2/20.
 */

public class ModelSignSource implements ModelSign {

    @Override
    public void sendVerificationModel(Context context, String phone, final PresenterSign presenterSign) {//发送验证码

        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        BmobSMS.requestSMSCode(context,phone, "E-teach",new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId,BmobException ex) {
                // TODO Auto-generated method stub
                if(ex==null){//验证码发送成功
                   presenterSign.onSuccess();
                }
                else//发送失败
                {
                   presenterSign.onFailure();
                }
            }
        });

    }

    @Override
    public void checkVerificationModel(Context context, String phone, String verification, final PresenterSign presenterSign){//检查验证码是否正确

        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        BmobSMS.verifySmsCode(context,phone, verification, new VerifySMSCodeListener() {

            @Override
            public void done(BmobException ex) {
                // TODO Auto-generated method stub
                if(ex==null){//短信验证码已验证成功
                   presenterSign.onSuccess();
                }else{
                   presenterSign.onFailure();
                }
            }
        });

    }

    @Override
    public void signUpModel(Context context, String phone, String username, String password, final PresenterSign presenterSign) {//手机号和密码注册

        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        final MyInfo myInfo=new MyInfo();
        myInfo.setPhone(phone);
        myInfo.setNickName(username);
        myInfo.setPassword(password);
        myInfo.save(context,new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ActivityCollector.myInfo=myInfo;
                presenterSign.onSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                presenterSign.onFailure();
            }
        });

    }

    @Override
    public void updatePasswordModel(Context context, String password, final PresenterSign presenterSign) {//更新密码
        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        final MyInfo myInfo=ActivityCollector.myInfo;
        myInfo.setPassword(password);
        myInfo.update(context, myInfo.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                ActivityCollector.myInfo=myInfo;
                presenterSign.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                presenterSign.onFailure();
            }
        });
    }

    @Override
    public void queryByPhoneModel(Context context, String phone, final PresenterSign presenterSign) {//根据手机号查询账户

        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        BmobQuery<MyInfo> query= new BmobQuery<MyInfo>();
        query.addWhereEqualTo("phone", phone);
        query.findObjects(context,new FindListener<MyInfo>() {
            @Override
            public void onSuccess(List<MyInfo> list) {
                if(list.size()==0)
                    presenterSign.onSuccess();
                else
                {
                    ActivityCollector.flag2=1;
                    ActivityCollector.myInfo=list.get(0);
                    presenterSign.onFailure();
                }
            }
            @Override
            public void onError(int code, String msg) {
                ActivityCollector.flag2=0;
                presenterSign.onFailure();
            }
        });

    }

    @Override
    public void signInByPhoneModel(Context context,String phone,final PresenterSign presenterSign) {//使用手机号和密码登陆

        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        BmobQuery<MyInfo> query= new BmobQuery<MyInfo>();
        query.addWhereEqualTo("phone", phone);
        query.findObjects(context,new FindListener<MyInfo>() {
            @Override
            public void onSuccess(List<MyInfo> list) {
                if(list.size()==0) {
                    ActivityCollector.flag2=1;
                    presenterSign.onFailure();
                }
                else
                {
                    ActivityCollector.myInfo=list.get(0);
                    presenterSign.onSuccess();
                }
            }
            @Override
            public void onError(int code, String msg) {
                ActivityCollector.flag2=0;
                presenterSign.onFailure();
            }
        });

    }

    @Override
    public void queryQQIdModel(Context context, String id, final PresenterSign presenterSign) {//根据QQ查询账户

        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        BmobQuery<MyInfo> query = new BmobQuery<MyInfo>();
        query.addWhereEqualTo("qqId",id);
        query.findObjects(context, new FindListener<MyInfo>() {
            @Override
            public void onSuccess(List<MyInfo> object) {
                if(object.size()==0){
                    ActivityCollector.flag2=1;
                    presenterSign.onFailure();
                }
                else{
                    ActivityCollector.myInfo=object.get(0);
                    presenterSign.onSuccess();
                }
            }

            @Override
            public void onError(int code, String msg) {
                ActivityCollector.flag2=0;
                presenterSign.onFailure();
            }
        });

    }

    @Override
    public void queryByWeixinIdModel(Context context,String id,PresenterSign presenterSign) {//根据微信查询账户
    }

    @Override
    public void queryByWeiboIdModel(Context context,String id,PresenterSign presenterSign) {//根据微博查询账户
    }
}
