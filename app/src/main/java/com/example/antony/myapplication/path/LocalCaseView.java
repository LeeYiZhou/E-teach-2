package com.example.antony.myapplication.path;

/**
 * Created by antony on 2018/3/3.
 */

public interface LocalCaseView {
    /**
     * 显示Toast
     * @param str Toast所展示的内容
     */
    void showToastView(String str);

    /**
     *显示进度条LoadingView
     */
    void showLoadingView();

    /**
     *关闭进度条LoadingView
     */
    void closeLoadingView();

}
