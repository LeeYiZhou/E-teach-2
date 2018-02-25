package com.example.antony.myapplication;

import android.app.Activity;

import com.example.antony.myapplication.data.MyInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antony on 2018/2/20.
 */

public class ActivityCollector {

    public static MyInfo myInfo=new MyInfo();
    public static String BmobApplicationId="655f4287cdd20115bf4d20cafb488dac";
    public static int flag1;//标志1
    public static int flag2;//标志2

    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }

}
