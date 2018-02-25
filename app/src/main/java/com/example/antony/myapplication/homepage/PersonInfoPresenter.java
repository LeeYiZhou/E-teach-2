package com.example.antony.myapplication.homepage;

import com.example.antony.myapplication.data.MyInfo;

/**
 * Created by antony on 2018/2/21.
 */

public class PersonInfoPresenter {

    private PersonInfoView personInfoView;
    private ModelHomepageSource modelHomepageSource;

    PersonInfoPresenter(PersonInfoView personInfoView){
        this.personInfoView=personInfoView;
        modelHomepageSource=new ModelHomepageSource();
    }

    public void uploadPhotoPresenter() {//上传图片
    }

    public void updateMyInfoPresenter() {//更新个人信息设置
    }

}
