package com.example.antony.myapplication.path;

/**
 * Created by antony on 2018/2/22.
 */

public interface ModelPath {
    boolean identifyPhotoModel();//鉴别缓则图片是否符合要求
    void cutPhotoModel();//裁剪图片
    void displayPhotoModel();//展示图片
}
