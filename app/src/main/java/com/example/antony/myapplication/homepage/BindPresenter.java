package com.example.antony.myapplication.homepage;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.data.MyInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by antony on 2018/2/23.
 */

public class BindPresenter {

    private BindView bindView;
    private ModelHomepageSource modelHomepageSource;
    private BindActivity bindActivity;
    private Tencent mTencent;
    private BaseUiListener listener;

    BindPresenter(BindActivity bindActivity,BindView bindView){
        this.bindActivity=bindActivity;
        this.bindView=bindView;
        modelHomepageSource=new ModelHomepageSource();
        mTencent= Tencent.createInstance(ActivityCollector.QQID,bindActivity);
        listener = new BaseUiListener();
    }

    public void bindQQPresenter(){
        mTencent.login(bindActivity,"all",listener);
    }

    public void startActivityPresenter(int requestCode, int resultCode, Intent data){
        Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener());
    }

    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {

            bindView.showToastView("开始授权");
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(o.toString());
                String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                final String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                        && !TextUtils.isEmpty(openId)) {
                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openId);

                    modelHomepageSource.queryQQIdModel(bindActivity, openId, new PresenterHomepage() {
                        @Override
                        public void onSuccess() {
                            modelHomepageSource.bindQQIdModel(bindActivity, openId, new PresenterHomepage() {
                                @Override
                                public void onSuccess() {
                                    bindView.showToastView("绑定成功");
                                }

                                @Override
                                public void onFailure() {
                                    bindView.showToastView("绑定失败");
                                }
                            });
                        }

                        @Override
                        public void onFailure() {
                            if(ActivityCollector.flag2==0)
                                bindView.showToastView("授权出错");
                            else if(ActivityCollector.flag2==1)
                                bindView.showToastView("该QQ已被绑定，请更换");
                        }
                    });
                }
                else
                    bindView.showToastView("授权出错");

            } catch (JSONException e) {
                e.printStackTrace();
                bindView.showToastView("授权出错");
            }
        }

        @Override
        public void onError(UiError e) {
            bindView.showToastView("Error");
        }

        @Override
        public void onCancel() {
            bindView.showToastView("撤销授权");
        }
    }

}
