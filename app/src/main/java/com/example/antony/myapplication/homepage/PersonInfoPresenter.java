package com.example.antony.myapplication.homepage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.data.MyInfo;
import com.example.antony.myapplication.util.PhotoUtils;

import java.util.ArrayList;

/**
 * Created by antony on 2018/2/21.
 */

public class PersonInfoPresenter {

    private PersonInfoView personInfoView;
    private ModelHomepageSource modelHomepageSource;
    private Context context;

    PersonInfoPresenter(Context context,PersonInfoView personInfoView){
        this.context=context;
        this.personInfoView=personInfoView;
        modelHomepageSource=new ModelHomepageSource();
    }

    public void updateMyInfoPresenter() {//更新个人信息设置
        ActivityCollector.flag1=0;
        personInfoView.openLoadingView();
        final ArrayList<String> list=personInfoView.showInfoView();
        if(list==null){
            ActivityCollector.flag1=1;
            personInfoView.closeLoadingView();
            personInfoView.showToastView("信息不能为空");
        }
        else{
            Uri cropImageUri=personInfoView.showCropImageUriView();
            if(cropImageUri==null){
                modelHomepageSource.updateMyInfoOneModel(context, list, new PresenterHomepage() {
                    @Override
                    public void onSuccess() {
                        ActivityCollector.flag1=1;
                        personInfoView.closeLoadingView();
                        personInfoView.showToastView("修改成功");
                    }

                    @Override
                    public void onFailure() {
                        ActivityCollector.flag1=1;
                        personInfoView.closeLoadingView();
                        personInfoView.showToastView("修改失败");
                    }
                });
            }
            else{
                String imagePath=PhotoUtils.getImageAbsolutePath(context,cropImageUri);
                modelHomepageSource.uploadImageModel(context, imagePath, new PresenterHomepage() {
                    @Override
                    public void onSuccess() {
                        modelHomepageSource.updateMyInfoTwoModel(context, list, new PresenterHomepage() {
                            @Override
                            public void onSuccess() {
                                ActivityCollector.flag1=1;
                                personInfoView.closeLoadingView();
                                personInfoView.showToastView("修改成功");
                            }

                            @Override
                            public void onFailure() {
                                ActivityCollector.flag1=1;
                                personInfoView.closeLoadingView();
                                personInfoView.showToastView("修改失败");
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        ActivityCollector.flag1=1;
                        personInfoView.closeLoadingView();
                        personInfoView.showToastView("修改失败");
                    }
                });
            }
        }

    }

}
