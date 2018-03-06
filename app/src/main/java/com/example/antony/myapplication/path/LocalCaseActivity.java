package com.example.antony.myapplication.path;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.homepage.HomepageActivity;
import com.example.antony.myapplication.homepage.PersonInfoActivity;
import com.example.antony.myapplication.predict.OutcomeDisplayPredictActivity;
import com.example.antony.myapplication.sign.MainActivity;
import com.example.antony.myapplication.train.EditCaseActivity;
import com.example.antony.myapplication.train.OutcomeDisplayTrainActivity;
import com.example.antony.myapplication.util.CustomVIsionAPI;
import com.example.antony.myapplication.util.FaceAPI;
import com.example.antony.myapplication.util.PhotoUtils;
import com.example.antony.myapplication.util.ScaleView;
import com.example.antony.myapplication.util.UltimateBar;
import com.mingle.widget.LoadingView;

import java.io.File;
import java.util.ArrayList;

public class LocalCaseActivity extends BaseActivity implements LocalCaseView{


    /*各个控件对象，例如：private TextView accountName*/
    private LoadingView loadingView;
    private ImageView upload;
    private RelativeLayout camera;
    private RelativeLayout album;
    private ScaleView scaleView;

    /*拍照 相册上传图片*/
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CODE_OPEN_CAMERA=0xa3;
    private static final int CODE_OPEN_ALBUM=0xa4;

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri=null;//原图片Uri
    private Uri cropImageUri=null;//裁剪后图片Uri

    /*数据*/
    private String type;
    private Bitmap bitmap;


    /*初始化方法*/
    /**
     * 初始化各个控件
     */
    private void initViews(){

        scaleView=(ScaleView) findViewById(R.id.image);
        upload=(ImageView)findViewById(R.id.upload);
        camera=(RelativeLayout)findViewById(R.id.camera);
        album=(RelativeLayout)findViewById(R.id.album);
        loadingView=(LoadingView)findViewById(R.id.loadView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * //初始化各个事件
     */
    private void initListener(){

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageUri=null;
                if(ContextCompat.checkSelfPermission(LocalCaseActivity.this,
                        Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED&&
                        ContextCompat.checkSelfPermission(LocalCaseActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(LocalCaseActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},
                            CODE_OPEN_CAMERA);
                }
                else
                    openCamera();
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageUri=null;
                if(ContextCompat.checkSelfPermission(LocalCaseActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(LocalCaseActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_OPEN_ALBUM);
                }
                else
                    openAlbum();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.flag=true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap == null) {
                            Log.e("Bitmap", "Bitmap null！");
                        } else {
                            Log.e("Bitmap", "Bitmap Not null！");
                        }
                        ActivityCollector.flag=CustomVIsionAPI.isImagePatient(bitmap);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LocalCaseActivity.this.showToastView("test"+ActivityCollector.flag);
                            }
                        });
                    }
                }).start();

                /*if(flag){
                    if(type.equals("path")){
                        Intent intent=new Intent(LocalCaseActivity.this,OutcomeDisplayPathActivity.class);
                        intent.putExtra("type","local");
                        startActivity(intent);
                    }
                    else if(type.equals("train")){
                        Intent intent=new Intent(LocalCaseActivity.this,EditCaseActivity.class);
                        intent.putExtra("type","local");
                        startActivity(intent);
                    }
                    else if(type.equals("predict")){
                        Intent intent=new Intent(LocalCaseActivity.this, OutcomeDisplayPredictActivity.class);
                        intent.putExtra("type","local");
                        startActivity(intent);
                    }
                    else if(type.equals("HomepageActivity")){
                        Intent intent=new Intent(LocalCaseActivity.this,HomepageActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    LocalCaseActivity.this.showToastView("输入的图片不是唇腭裂患者");
                }
                bitmap=FaceAPI.detectAndCrop(bitmap);
                scaleView.setImageBitmap(bitmap);
                LocalCaseActivity.this.showToastView("cheng");*/
            }
        });

    }

    /**
     * 初始化其他
     */
    private void initOthers(){//其他初始化操作
        loadingView.setLoadingText("正在上传，请耐心等待");
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
    }

    /*初始化方法*/


    /*重写BaseActivity中的方法*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_case);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.light_red));
        initViews();
        initOthers();
        initListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        int output_X = 480, output_Y = 480;
        if (resultCode == PersonInfoActivity.RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));//注意
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.donkor.demo.takephoto.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else
                        LocalCaseActivity.this.showToastView("设备没有SD卡!");
                    break;
                case CODE_RESULT_REQUEST:
                    bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        scaleView.setImageBitmap(bitmap);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    /*重写BaseActivity中的方法*/


    /*重写LocalCaseView中的方法*/
    @Override
    public void showToastView(String str){
        Toast.makeText(LocalCaseActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView(){
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeLoadingView(){
        loadingView.setVisibility(View.INVISIBLE);
    }

    /*重写LocalCaseView中的方法*/


    /*调用相册、相机上传图片*/

    /**
     * 打开相册
     */
    private void openAlbum(){
        PhotoUtils.openPic(LocalCaseActivity.this, CODE_GALLERY_REQUEST);
    }

    /**
     * 打开相机
     */
    private void openCamera(){
        if (hasSdcard()) {
            imageUri = Uri.fromFile(fileUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                //通过FileProvider创建一个content类型的Uri
                imageUri = FileProvider.getUriForFile(LocalCaseActivity.this, "com.example.antony.myapplication.fileprovider", fileUri);
            PhotoUtils.takePicture(LocalCaseActivity.this, imageUri, CODE_CAMERA_REQUEST);
        } else
            LocalCaseActivity.this.showToastView("设备没有SD卡");
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     * @return SDCard是否存在
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /*调用相册、相机上传图片*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(LocalCaseActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
        return true;

    }



}
