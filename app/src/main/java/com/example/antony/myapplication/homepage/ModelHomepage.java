package com.example.antony.myapplication.homepage;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.example.antony.myapplication.data.MyInfo;

import java.util.ArrayList;

/**
 * Created by antony on 2018/2/21.
 */

public interface ModelHomepage {
    void updateMyInfoOneModel(Context context,ArrayList<String> list,PresenterHomepage presenterHomepage);//更新个人信息设置,不更新头像
    void updateMyInfoTwoModel(Context context,ArrayList<String> list,PresenterHomepage presenterHomepage);//更新个人信息设置,更新头像
    void queryQQIdModel(Context context,String id,PresenterHomepage presenterHomepage);//查询QQId
    void bindQQIdModel(Context context,String id,PresenterHomepage presenterHomepage);//绑定QQId
    void uploadImageModel(Context context,String imagePath,PresenterHomepage presenterHomepage);//上传图片
}
