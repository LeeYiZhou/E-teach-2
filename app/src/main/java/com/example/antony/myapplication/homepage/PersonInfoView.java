package com.example.antony.myapplication.homepage;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by antony on 2018/2/21.
 */

public interface PersonInfoView {
    void showToastView(String str);
    Uri showCropImageUriView();//获取裁剪后图片Uri;
    ArrayList<String> showInfoView();//获取用户信息
    void openLoadingView();//打开加载界面
    void closeLoadingView();//关闭界面
}
