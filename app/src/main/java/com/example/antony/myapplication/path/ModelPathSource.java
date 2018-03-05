package com.example.antony.myapplication.path;

import android.content.Context;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.ModelToPresenterCallback;
import com.example.antony.myapplication.data.Image;
import com.example.antony.myapplication.data.MyInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by antony on 2018/2/22.
 */

public class ModelPathSource implements ModelPath {

    /**
     * 无条件查询图片
     */
    @Override
    public void queryImagesView(Context context, final ModelToPresenterCallback modelToPresenterCallback){
        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        BmobQuery<Image> query= new BmobQuery<Image>();
        query.order("-createdAt");
        query.setLimit(4);
        query.findObjects(context,new FindListener<Image>() {
            @Override
            public void onSuccess(List<Image> list) {
               ActivityCollector.imageList=list;
               modelToPresenterCallback.onSuccess();
            }
            @Override
            public void onError(int code, String msg) {
               modelToPresenterCallback.onFailure();
            }
        });
    }

    /**
     * 有条件查询图片
     */
    @Override
    public void conditionQueryImagesView(Context context,String lastDate,final ModelToPresenterCallback modelToPresenterCallback){
        Bmob.initialize(context, ActivityCollector.BmobApplicationId);//初始化
        BmobQuery<Image> query= new BmobQuery<Image>();
        query.order("-createdAt");
        query.setLimit(4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(lastDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.addWhereLessThan("createdAt", new BmobDate(date));
        query.findObjects(context,new FindListener<Image>() {
            @Override
            public void onSuccess(List<Image> list) {
                ActivityCollector.imageList=list;
                modelToPresenterCallback.onSuccess();
            }
            @Override
            public void onError(int code, String msg) {
                modelToPresenterCallback.onFailure();
            }
        });
    }
}
