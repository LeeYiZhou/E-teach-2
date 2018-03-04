package com.example.antony.myapplication.util;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.PersonGroup;
import com.microsoft.projectoxford.face.contract.VerifyResult;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

// 由于要进行费时操作，执行函数时需要另开线程

public class FaceAPI {

    // private final static String personGroupId = "facegroup_tomo";

    private static FaceServiceClient faceServiceClient =
            new FaceServiceRestClient(
                    "https://eastasia.api.cognitive.microsoft.com/face/v1.0",
                    "aa54bebcf55e4bf7a0d21ff16bd054cc"
            );

    // 用于判断2张人脸是否属于同一个人（如果是同一张图片，显然是）
    public static boolean isFaceFromSamePerson(Context context, int resId1, int resId2) {
        return isFaceFromSamePerson(readBitmapById(context, resId1), readBitmapById(context, resId2));
    }

    // 用于判断2张人脸是否属于同一个人（如果是同一张图片，显然是）
    public static boolean isFaceFromSamePerson(Bitmap img1, Bitmap img2) {
        try {
            Face[] resultImg1 = detectFaceFromBitmap(img1);
            Face[] resultImg2 = detectFaceFromBitmap(img2);
            if(resultImg1 == null || resultImg2 == null) {
                return false;
            }
            Face faceImg1 = resultImg1[0];
            Face faceImg2 = resultImg2[0];
            VerifyResult verifyResult = faceServiceClient.verify(faceImg1.faceId, faceImg2.faceId);
            return verifyResult.isIdentical;
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如果中途出现异常，也返回false
        return false;
    }

    // 侦测人脸并返回裁剪后的bitmap
    public static Bitmap detectAndCrop(Context context, int resId) {
        return detectAndCrop(readBitmapById(context, resId));
    }

    // 侦测人脸并返回裁剪后的bitmap
    public static Bitmap detectAndCrop(final Bitmap imageBitmap)
    {
        try {
            Face[] result = detectFaceFromBitmap(imageBitmap);
            if(result == null) {
                return null;   // 返回null表示没有侦测到人脸
            }
            else {
                Face face = result[0];
                int left = face.faceRectangle.left;
                int top = face.faceRectangle.top;
                int width = face.faceRectangle.width;
                int height = face.faceRectangle.height;
                Bitmap outputBitmap = Bitmap.createBitmap(imageBitmap, left, top, width, height);
                return outputBitmap;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // 中途出现异常，也返回null
        return null;
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

    private static Face[] detectFaceFromBitmap(final Bitmap imageBitmap) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            ByteArrayInputStream inputStream =
                    new ByteArrayInputStream(outputStream.toByteArray());
            Face[] result = faceServiceClient.detect(
                    inputStream,
                    true,         // returnFaceId
                    false,        // returnFaceLandmarks
                    null           // returnFaceAttributes: a string like "age, gender"
            );
            if(result == null) {
                return null;   // 返回null表示没有侦测到人脸
            }
            else {
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // 中途出现异常，也返回null
        return null;
    }
}
