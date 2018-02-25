package com.example.antony.myapplication.homepage;

import com.example.antony.myapplication.data.MyInfo;

/**
 * Created by antony on 2018/2/21.
 */

public interface ModelHomepage {
    boolean identifyPhotoModel();//鉴别缓则图片是否符合要求
    void cutPhotoModel();//裁剪图片
    void uploadPhotoModel();//上传图片
    void updateMyInfoModel(MyInfo myInfo);//更新个人信息设置
}
