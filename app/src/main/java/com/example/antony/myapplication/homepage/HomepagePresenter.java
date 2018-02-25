package com.example.antony.myapplication.homepage;

/**
 * Created by antony on 2018/2/21.
 */

public class HomepagePresenter{

    private HomepageView homepageView;
    private ModelHomepageSource modelHomepageSource;

    HomepagePresenter(HomepageView homepageView){
        this.homepageView=homepageView;
        modelHomepageSource=new ModelHomepageSource();
    }

    public void identifyPhotoPresenter() {//鉴别缓则图片是否符合要求
    }

    public void cutPhotoPresenter() {//裁剪图片
    }

    public void uploadPhotoPresenter() {//上传图片
    }

}
