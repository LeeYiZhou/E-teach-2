package com.example.antony.myapplication.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.baidu.aip.face.AipFace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by 40178 on 2018/3/6.
 */

public class BaiduFaceAPI {

    private static final String TAG = "BaifuFaceAPI";

    private static final String APP_ID = "10876502";
    private static final String API_KEY = "0IIjqH9xRxz0Q1zGM0SURbOl";
    private static final String SECRET_KEY = "L9RMXFIFyo66vZaXOkcRb4ZGzL2cpMRZ ";

    private static final AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

    private static final String GROUP_ID = "patient";

    private static HashMap<String, String> name2id = new HashMap<>();

    static {
        name2id.put("", "");
        
    }

    public static final String getPatientName(String imgUrl) {
        String uid = null;
        try {
            JSONObject response = client.identifyUser(GROUP_ID, imgUrl, new HashMap<String, String>());
            JSONArray identifyResult = response.optJSONArray("result");
            if (identifyResult == null || identifyResult.length() == 0) {
                Log.e(TAG, "没有找到人脸，或者不是组内的人脸");
                return null; // 如果不是组内的人脸，返回null
            }
            JSONObject resultObj = identifyResult.getJSONObject(0);
            uid = resultObj.optString("uid");
            return uid;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "API调用过程出现Exception");
        }
        return null;
    }
}
