package com.example.antony.myapplication.sign;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;

import cn.bmob.v3.Bmob;

public class SignInActivity extends BaseActivity implements SignInView,View.OnClickListener{

    //各个控件对象，例如：private TextView accountName
    private EditText phone;//手机号
    private EditText password;//密码
    private TextView forgetPassword;//忘记密码
    private TextView signInByFace;//人脸识别登陆
    private TextView signUp;//注册
    private Button  signIn;//登陆按钮
    private ImageView signInByQQ;//qq登陆按钮
    private ImageView signInByWeixin;//微信登陆按钮
    private ImageView signInByWeibo;//微博登陆按钮
    private SignInPresenter signInPresenter;

    //popupwindow
    Button popFind;
    Button popVerification;
    Button popCancel;
    PopupWindow popup;

    //自定义函数
    private void initViews(){//初始化各个控件

        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        forgetPassword=(TextView)findViewById(R.id.txt_forget_password);
        signInByFace=(TextView)findViewById(R.id.txt_face_recog);
        signUp=(TextView)findViewById(R.id.sign_up);
        signIn=(Button)findViewById(R.id.sign_in);
        signInByQQ=(ImageView)findViewById(R.id.img_qq);
        signInByWeixin=(ImageView)findViewById(R.id.weixin);
        signInByWeibo=(ImageView)findViewById(R.id.weibo);

    }

    private void initListener(){//初始化各个事件

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCollector.flag1==1)
                    signInPresenter.signInByPhonePresenter();
                else
                    SignInActivity.this.showToastView("正在登陆，请耐心等待");
            }
        });

        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(SignInActivity.this,SignUpOneActivity.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {//开启popupwindow
            @Override
            public void onClick(View view) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp); //设置背景变暗
                popup.showAtLocation(findViewById(R.id.bottom_view), Gravity.BOTTOM, 0, 0);
            }
        });
        signInByQQ.setOnClickListener(this);
        signInByWeixin.setOnClickListener(this);
        signInByWeibo.setOnClickListener(this);
        signInByFace.setOnClickListener(this);

    }

    private void initPop(){//初始化popupwindow
        View view = LayoutInflater.from(SignInActivity.this).inflate(R.layout.popupwindow_sign_in, null);
        popup= new PopupWindow(view.getContext());
        popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //popupWindow.setElevation(10.0f);
        popup.setContentView(view);
        popup.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popup.setOutsideTouchable(false);
        popup.setFocusable(true);
        popup.setAnimationStyle(R.style.popup_anim);

        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        popFind= (Button) view.findViewById(R.id.find);
        popFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ForgetOneActivity.class);
                intent.putExtra("type","find");
                startActivity(intent);
            }
        });

        popVerification = (Button) view.findViewById(R.id.verification);
        popVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ForgetOneActivity.class);
                intent.putExtra("type","verification");
                startActivity(intent);
            }
        });

        popCancel=(Button)view.findViewById(R.id.cancel);
        popCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });
    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ActivityCollector.flag1=1;
        signInPresenter=new SignInPresenter(SignInActivity.this,SignInActivity.this);
        initViews();
        initListener();
        initPop();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_qq:
                signInPresenter.signInByQQPresenter();
                break;
            case R.id.weixin:
            case R.id.weibo:
            case R.id.txt_face_recog:
                SignInActivity.this.showToastView("正在开发，敬请期待");
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        signInPresenter.startActivityPresenter(requestCode,resultCode,data);
    }

    //重写SignInView中的方法
    @Override
    public void showToastView(String str) {//显示Toast
        Toast.makeText(SignInActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String showPhoneView() {//获得手机号

        String number=phone.getText().toString();
        if(TextUtils.isEmpty(number)||number.length()!=11)
            return null;
        else
            return number;
    }

    @Override
    public String showPasswordView() {//获得密码

        String number=password.getText().toString();
        if(TextUtils.isEmpty(number))
            return null;
        return number;
    }

    @Override
    public void startActivityView() {//跳转到注册界面
        Intent intent=new Intent(SignInActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
