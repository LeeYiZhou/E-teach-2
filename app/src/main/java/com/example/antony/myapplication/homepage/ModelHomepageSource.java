package com.example.antony.myapplication.homepage;

import android.content.Context;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.data.MyInfo;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by antony on 2018/2/21.
 */

public class ModelHomepageSource implements ModelHomepage {

    @Override
    public void queryQQIdModel(Context context, String id, final PresenterHomepage presenterHomepage) {//查询QQId

        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        BmobQuery<MyInfo> query = new BmobQuery<MyInfo>();
        query.addWhereEqualTo("qqId",id);
        query.findObjects(context, new FindListener<MyInfo>() {
            @Override
            public void onSuccess(List<MyInfo> object) {
                if(object.size()!=0){
                    ActivityCollector.flag2=1;
                    presenterHomepage.onFailure();
                }
                else
                    presenterHomepage.onSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                ActivityCollector.flag2=0;
               presenterHomepage.onFailure();
            }
        });
    }

    @Override
    public void bindQQIdModel(Context context, String id, final PresenterHomepage presenterHomepage) {//绑定QQId

        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        final MyInfo myInfo=ActivityCollector.myInfo;
        myInfo.setQqId(id);
        myInfo.update(context,myInfo.getObjectId(), new UpdateListener(){

            @Override
            public void onSuccess() {
                ActivityCollector.myInfo=myInfo;
                presenterHomepage.onSuccess();
            }

            @Override
            public void onFailure(int code, String msg){
                presenterHomepage.onFailure();
            }
        });

    }

    @Override
    public boolean identifyPhotoModel() {//鉴别缓则图片是否符合要求
        return true;
    }

    @Override
    public void cutPhotoModel() {//裁剪图片
    }

    @Override
    public void uploadPhotoModel() {//上传图片
    }

    @Override
    public void updateMyInfoModel(MyInfo myInfo){//更新个人信息设置

    }

}
