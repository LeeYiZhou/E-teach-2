package com.example.antony.myapplication.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.data.MyInfo;
import com.example.antony.myapplication.sign.MainActivity;

public class HomepageActivity extends BaseActivity implements HomepageView{

    //各个控件对象，例如：private TextView accountName
    private TextView username;
    private TextView school;
    private LinearLayout setting;
    private LinearLayout caseUpload;

    //自定义函数
    private void initViews(){//初始化各个控件
        username=(TextView)findViewById(R.id.username);
        school=(TextView)findViewById(R.id.school);
        setting=(LinearLayout)findViewById(R.id.setting);
        caseUpload=(LinearLayout)findViewById(R.id.case_upload);
    }

    private void initListener(){//初始化各个事件
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomepageActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow
        MyInfo myInfo=ActivityCollector.myInfo;
        username.setText(myInfo.getNickName().toString());
        if(myInfo.getSchool()==null)
            school.setText("主页君很懒，没留下任何足迹");
        else
            school.setText(myInfo.getSchool().toString());
    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initViews();
        initListener();
        initOthers();
    }

    //重写HomepageView中的方法
}
