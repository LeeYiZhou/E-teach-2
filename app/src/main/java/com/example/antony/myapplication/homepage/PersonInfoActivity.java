package com.example.antony.myapplication.homepage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.data.MyInfo;
import com.example.antony.myapplication.sign.MainActivity;
import com.example.antony.myapplication.util.PhotoUtils;
import com.example.antony.myapplication.util.PickerView;
import com.mingle.widget.LoadingView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfoActivity extends BaseActivity implements PersonInfoView,View.OnClickListener{

    //初始化各个对象，例如：private TextView accountName
    private CircleImageView icon;
    private TextView signature;
    private RelativeLayout caseDB;
    private RelativeLayout course;
    private EditText name;
    private EditText school;
    private EditText location;
    private EditText mail;
    private EditText signatureEdit;
    private Button save;
    private LoadingView loadingView;
    private PersonInfoPresenter personInfoPresenter;

    //个人信息中性别设置所需
    private List<String> sexList = new ArrayList<>();
    private PickerView sexPicker;
    private PopupWindow popupSex;
    private TextView popSexCancel;
    private TextView popSexConfirm;
    private RelativeLayout sexLayout;
    private TextView sex;

    //个人信息中生日设置所需
    private List<String> birthYearList = new ArrayList<>();
    private List<String> birthMonthList = new ArrayList<>();
    private List<String> birthDayList = new ArrayList<>();
    private PickerView birthYearPicker;
    private PickerView birthMonthPicker;
    private PickerView birthDayPicker;
    private PopupWindow popupBirth;
    private TextView popBirthCancel;
    private TextView popBirthConfirm;
    private RelativeLayout birthdayLayout;
    private TextView birthday;

    //popupwindow
    PopupWindow popupIcon;
    private Button album;
    private Button photo;
    private Button cancel;

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

    //初始化操作
    private void initViews(){//初始化各个控件
        icon=(CircleImageView)findViewById(R.id.icon);
        signature=(TextView)findViewById(R.id.signature);
        caseDB=(RelativeLayout)findViewById(R.id.caseDB);
        course=(RelativeLayout)findViewById(R.id.course);
        name=(EditText)findViewById(R.id.name);
        sexLayout=(RelativeLayout)findViewById(R.id.sex_picker);
        sex=(TextView)findViewById(R.id.sex);
        birthdayLayout=(RelativeLayout)findViewById(R.id.birthday_picker);
        birthday=(TextView)findViewById(R.id.birhday);
        school=(EditText)findViewById(R.id.school);
        location=(EditText)findViewById(R.id.location);
        mail=(EditText)findViewById(R.id.mail);
        signatureEdit=(EditText)findViewById(R.id.signature_edit);
        loadingView=(LoadingView)findViewById(R.id.loadView);
        save=(Button)findViewById(R.id.save);
    }

    private void initValues(){//赋初值
        MyInfo myInfo= ActivityCollector.myInfo;
        if(!TextUtils.isEmpty(myInfo.getIcon()))
            Glide.with(PersonInfoActivity.this).load(myInfo.getIcon()).into(icon);
        if(!TextUtils.isEmpty(myInfo.getSignature())){
            signature.setText("#"+myInfo.getSignature()+"#");
            signatureEdit.setText(myInfo.getSignature());
        }
        if(!TextUtils.isEmpty(myInfo.getNickName()))
            name.setText(myInfo.getNickName());
        if(!TextUtils.isEmpty(myInfo.getSex()))
            sex.setText(myInfo.getSex());
        if(!TextUtils.isEmpty(myInfo.getBirthday()))
            birthday.setText(myInfo.getBirthday());
        if(!TextUtils.isEmpty(myInfo.getSchool()))
            school.setText(myInfo.getSchool());
        if(!TextUtils.isEmpty(myInfo.getLocation()))
            location.setText(myInfo.getLocation());
        if(!TextUtils.isEmpty(myInfo.getMail()))
            mail.setText(myInfo.getMail());

    }

    private void initListener(){//初始化各个事件
        icon.setOnClickListener(this);
        caseDB.setOnClickListener(this);
        course.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        birthdayLayout.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void initPopSex(){//初始化性别popupwindow
        sexList.add("男");
        sexList.add("女");
        View contentView = LayoutInflater.from(PersonInfoActivity.this).inflate(R.layout.popup_select_sex_style, null);
        popupSex = new PopupWindow(contentView);
        popupSex.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupSex.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupSex.setBackgroundDrawable(new ColorDrawable(0x00000000));//不这样无法通过点击空白处退出
        popupSex.setOutsideTouchable(true);
        popupSex.setFocusable(true);
        popupSex.setAnimationStyle(R.style.popup_anim);
        sexPicker = (PickerView) contentView.findViewById(R.id.sex_picker);
        sexPicker.setData(sexList);

        popupSex.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        popSexCancel = (TextView)contentView.findViewById(R.id.cancel);
        popSexCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupSex.dismiss();
            }
        });

        popSexConfirm = (TextView)contentView.findViewById(R.id.confirm);
        popSexConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex.setText(sexPicker.getCurSelected() );
                popupSex.dismiss();
            }
        });
    }

    private void initPopBirthday(){//初始化生日popupwindow
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        for(int i = 1910; i <=year; i++) {
            birthYearList.add("" + i);
        }
        for(int i = 1; i <= 12; i++) {
            birthMonthList.add("" + i);
        }
        for(int i = 1; i <= 31; i++) {
            birthDayList.add("" + i);
        }

        View contentView = LayoutInflater.from(PersonInfoActivity.this).inflate(R.layout.popup_select_birthday_style, null);

        popupBirth = new PopupWindow(contentView);

        popupBirth.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupBirth.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupBirth.setBackgroundDrawable(new ColorDrawable(0x00000000));//不这样无法通过点击空白处退出

        popupBirth.setOutsideTouchable(true);
        popupBirth.setFocusable(true);
        popupBirth.setAnimationStyle(R.style.popup_anim);

        birthYearPicker = (PickerView) contentView.findViewById(R.id.birth_year_picker);
        birthMonthPicker = (PickerView) contentView.findViewById(R.id.birth_month_picker);
        birthDayPicker = (PickerView) contentView.findViewById(R.id.birth_day_picker);

        birthYearPicker.setData(birthYearList);
        birthMonthPicker.setData(birthMonthList);
        birthDayPicker.setData(birthDayList);

        popupBirth.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        popBirthCancel = (TextView)contentView.findViewById(R.id.cancel);
        popBirthCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBirth.dismiss();
            }
        });

        popBirthConfirm = (TextView)contentView.findViewById(R.id.confirm);
        popBirthConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthday.setText(birthYearPicker.getCurSelected() + "-" +
                        birthMonthPicker.getCurSelected() + "-" +
                        birthDayPicker.getCurSelected());
                popupBirth.dismiss();
            }
        });
    }

    private void initPopIcon(){//初始化头像popupwindow
        View view = LayoutInflater.from(PersonInfoActivity.this).inflate(R.layout.popupwindow_local_case, null);
        popupIcon= new PopupWindow(view.getContext());
        popupIcon.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupIcon.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //popupWindow.setElevation(10.0f);
        popupIcon.setContentView(view);
        popupIcon.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupIcon.setOutsideTouchable(false);
        popupIcon.setFocusable(true);
        popupIcon.setAnimationStyle(R.style.popup_anim);

        popupIcon.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        album= (Button) view.findViewById(R.id.album);
        album.setOnClickListener(this);

        photo = (Button) view.findViewById(R.id.photo);
        photo.setOnClickListener(this);

        cancel=(Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
    }

    private void initPopupwindow(){//初始化popupwindow
        initPopIcon();
        initPopSex();
        initPopBirthday();
    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initViews();
        initPopupwindow();
        initValues();
        initListener();
        personInfoPresenter=new PersonInfoPresenter(PersonInfoActivity.this,PersonInfoActivity.this);
        ActivityCollector.flag1=1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        PersonInfoActivity.this.showToastView("设备没有SD卡!");
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                       icon.setImageBitmap(bitmap);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case CODE_OPEN_ALBUM:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    openAlbum();
                else PersonInfoActivity.this.showToastView("无法打开相册，因为你拒绝了该权限");
                break;
            case CODE_OPEN_CAMERA:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED
                        &&grantResults[1]==PackageManager.PERMISSION_GRANTED)
                    openCamera();
                else PersonInfoActivity.this.showToastView("无法打开相机，因为你拒绝了该权限");
                break;
        }
    }

    //重写View.OnClickListener中的方法
    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.icon:
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp); //设置背景变暗
                popupIcon.showAtLocation(findViewById(R.id.bottom_view), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.album:
                cropImageUri=null;
                if(ContextCompat.checkSelfPermission(PersonInfoActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PersonInfoActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_OPEN_ALBUM);
                }
                else
                    openAlbum();
                break;
            case R.id.photo:
                cropImageUri=null;
                if(ContextCompat.checkSelfPermission(PersonInfoActivity.this,
                        Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED&&
                        ContextCompat.checkSelfPermission(PersonInfoActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PersonInfoActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},
                            CODE_OPEN_CAMERA);
                }
                else
                    openCamera();
                break;
            case R.id.cancel:
                popupIcon.dismiss();
                break;
            case R.id.caseDB:
            case R.id.course:
                PersonInfoActivity.this.showToastView("正在开发，敬请期待");
                break;
            case R.id.sex_picker:
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = 0.7f;
                getWindow().setAttributes(lp2); //设置背景变暗
                popupSex.showAtLocation(findViewById(R.id.bottom_view), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.birthday_picker:
                WindowManager.LayoutParams lp3 = getWindow().getAttributes();
                lp3.alpha = 0.7f;
                getWindow().setAttributes(lp3); //设置背景变暗
                popupBirth.showAtLocation(findViewById(R.id.bottom_view), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.save:
                if(ActivityCollector.flag1==1)
                    personInfoPresenter.updateMyInfoPresenter();
                else
                    PersonInfoActivity.this.showToastView("正在修改，请耐心等待");
                break;
        }

    }

    //重写PersonInfoView中的方法
    @Override
    public Uri showCropImageUriView() {//获取裁剪后图片Uri;
        return cropImageUri;
    }

    @Override
    public void openLoadingView() {//打开加载界面
        loadingView.setLoadingText("正在保存，请耐心等待");
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeLoadingView() {//关闭界面
        loadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToastView(String str){
        Toast.makeText(PersonInfoActivity.this, str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public ArrayList<String> showInfoView() {//获取用户信息
        ArrayList<String> list=new ArrayList<>();
        list.add(name.getText().toString());
        list.add(sex.getText().toString());
        list.add(birthday.getText().toString());
        list.add(school.getText().toString());
        list.add(location.getText().toString());
        list.add(mail.getText().toString());
        list.add(signatureEdit.getText().toString());
        for(String str:list) {
            if(TextUtils.isEmpty(str))
                return null;
        }
        return list;
    }

    //调用相册、相机相关方法
    private void openAlbum(){//打开相册
        PhotoUtils.openPic(PersonInfoActivity.this, CODE_GALLERY_REQUEST);
    }

    private void openCamera(){//打开相机
        if (hasSdcard()) {
            imageUri = Uri.fromFile(fileUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                //通过FileProvider创建一个content类型的Uri
                imageUri = FileProvider.getUriForFile(PersonInfoActivity.this, "com.example.antony.myapplication.fileprovider", fileUri);
            PhotoUtils.takePicture(PersonInfoActivity.this, imageUri, CODE_CAMERA_REQUEST);
        } else
            PersonInfoActivity.this.showToastView("设备没有SD卡");
    }

    public static boolean hasSdcard() {//检查设备是否存在SDCard的工具方法
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
