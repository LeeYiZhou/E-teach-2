package com.example.antony.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by 40178 on 2018/3/3.
 */

public class CustomVIsionAPI {

    public static boolean isImagePatient(Context context, int resId) {
        return isImagePatient(readBitmapById(context, resId));
    }

    public static boolean isImagePatient(Bitmap imgBitmap) {

        HttpClient httpclient = new DefaultHttpClient();

        try
        {
            URI uri = new URI(
                    "https://southcentralus.api.cognitive.microsoft.com/" +
                            "customvision/v1.1/Prediction/" +
                            "ed452434-110d-4c4d-9c84-51bad8820b04/" +
                            "image?iterationId=9f31ee2f-46b9-450a-b95d-419f4abb669c");
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Prediction_Key", "b0df046c5d744954b4cb2690b3157fe9");

            // Request body
            byte[] imgByte = bitmap2Bytes(imgBitmap);

            ByteArrayEntity bae = new ByteArrayEntity(imgByte);
            request.setEntity(bae);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                String jsonString = EntityUtils.toString(entity).trim();
                JSONObject responseObject = new JSONObject(jsonString);
                JSONArray predictionsArray = responseObject.optJSONArray("Predictions");
                for(int i = 0; i < predictionsArray.length(); i++) {
                    JSONObject prediction = predictionsArray.getJSONObject(i);
                    String tag = prediction.optString("Tag");
                    if(tag.equals("0")) {
                        continue;
                    } else {
                        double prob = prediction.optDouble("Probability");
                        if(prob >= 0.9) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // 可以根据resourceID进行bitmap的载入
    private static Bitmap readBitmapById(Context context, int resId)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
