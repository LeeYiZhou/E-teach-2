package com.example.antony.myapplication.homepage;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.data.MyInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


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
    public void updateMyInfoOneModel(Context context, ArrayList<String> list, final PresenterHomepage presenterHomepage) {//更新个人信息设置,不更新头像
        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        final MyInfo myInfo=ActivityCollector.myInfo;
        myInfo.setNickName(list.get(0));
        myInfo.setSex(list.get(1));
        myInfo.setBirthday(list.get(2));
        myInfo.setSchool(list.get(3));
        myInfo.setLocation(list.get(4));
        myInfo.setMail(list.get(5));
        myInfo.setSignature(list.get(6));
        myInfo.update(context, myInfo.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                ActivityCollector.myInfo=myInfo;
                presenterHomepage.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                presenterHomepage.onFailure();
            }
        });
    }

    @Override
    public void updateMyInfoTwoModel(Context context,ArrayList<String> list,final PresenterHomepage presenterHomepage) {//更新个人信息设置,更新头像
        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        final MyInfo myInfo=ActivityCollector.myInfo;
        myInfo.setNickName(list.get(0));
        myInfo.setSex(list.get(1));
        myInfo.setBirthday(list.get(2));
        myInfo.setSchool(list.get(3));
        myInfo.setLocation(list.get(4));
        myInfo.setMail(list.get(5));
        myInfo.setSignature(list.get(6));
        myInfo.setIcon(ActivityCollector.intermediate);
        myInfo.update(context, myInfo.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                ActivityCollector.myInfo=myInfo;
                presenterHomepage.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                presenterHomepage.onFailure();
            }
        });
    }

    @Override
    public void uploadImageModel(final Context context, String imagePath, final PresenterHomepage presenterHomepage) {//上传图片
        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        final BmobFile bmobFile = new BmobFile(new File(imagePath));
        bmobFile.uploadblock(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                ActivityCollector.intermediate=bmobFile.getFileUrl(context);
                presenterHomepage.onSuccess();
            }

            @Override
            public void onProgress(Integer value) {

            }

            @Override
            public void onFailure(int code, String msg) {

                Log.w("MODEL",msg);
                presenterHomepage.onFailure();
            }

        });

    }

}

