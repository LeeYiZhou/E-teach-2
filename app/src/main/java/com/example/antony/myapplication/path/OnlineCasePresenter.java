package com.example.antony.myapplication.path;

import android.content.Context;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.ModelToPresenterCallback;

/**
 * Created by antony on 2018/2/22.
 */

public class OnlineCasePresenter {
    /*初始化对象*/
    private Context context;
    private OnlineCaseView onlineCaseView;
    private ModelPathSource modelPathSource;

    OnlineCasePresenter(Context context,OnlineCaseView onlineCaseView){
        this.onlineCaseView= onlineCaseView;
        modelPathSource=new ModelPathSource();
        this.context=context;
    }

    /**
     *初始化图片
     */
    public void initImagesPresenter(){
        modelPathSource.queryImagesView(context, new ModelToPresenterCallback() {
            @Override
            public void onSuccess() {
                onlineCaseView.initAdapterView(ActivityCollector.imageList);
            }

            @Override
            public void onFailure() {
                onlineCaseView.showToastView("获取数据失败");
            }
        });
    }

    /**
     * 增加图片
     */
    public void addImagesPresenter(){
        String lastDate=onlineCaseView.showLastDateView();
        modelPathSource.conditionQueryImagesView(context, lastDate, new ModelToPresenterCallback() {
            @Override
            public void onSuccess() {
                onlineCaseView.addAdapterView(ActivityCollector.imageList);
            }

            @Override
            public void onFailure() {
                onlineCaseView.showToastView("获取数据失败");
            }
        });
    }
}
