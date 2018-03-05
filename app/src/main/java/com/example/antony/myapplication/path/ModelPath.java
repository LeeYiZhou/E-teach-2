package com.example.antony.myapplication.path;

import android.content.Context;

import com.example.antony.myapplication.ModelToPresenterCallback;

/**
 * Created by antony on 2018/2/22.
 */

public interface ModelPath {
    /**
     *无条件查询图片
     */
    void queryImagesView(Context context,ModelToPresenterCallback modelToPresenterCallback);

    /**
     * 有条件查询图片
     */
     void conditionQueryImagesView(Context context,String lastDate,ModelToPresenterCallback modelToPresenterCallback);
}
