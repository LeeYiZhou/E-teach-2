package com.example.antony.myapplication.path;

import com.example.antony.myapplication.data.Image;

import java.util.List;

/**
 * Created by antony on 2018/2/22.
 */

public interface OnlineCaseView {
    void showToastView(String str);

    /**
     * 初始化Adapter
     * @param imageList 初始化数据
     */
    void initAdapterView(List<Image> imageList);
    /**
     * 给Adapter添加数据
     */
    void addAdapterView(List<Image> imageList);

    /**
     * 获取最后一个数据的创建时间
     * @return 最后一个数据的创建时间
     */
    String showLastDateView();
}
