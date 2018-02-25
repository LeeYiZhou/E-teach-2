package com.example.antony.myapplication.path;

/**
 * Created by antony on 2018/2/22.
 */

public class ModelPathSource implements ModelPath {

    @Override
    public boolean identifyPhotoModel() {//鉴别缓则图片是否符合要求
        return true;
    }

    @Override
    public void cutPhotoModel() {//裁剪图片
    }

    @Override
    public void displayPhotoModel() {//展示图片
    }
}
