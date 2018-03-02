package com.example.antony.myapplication.path;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.mingle.widget.LoadingView;

public class LocalCaseActivity extends BaseActivity {

    //各个控件对象，例如：private TextView accountName
    private LoadingView loadingView;
    private ImageView upload;
    private RelativeLayout camera;
    private RelativeLayout album;
    private ImageView imageView;

    //自定义函数
    private void initViews(){//初始化各个控件

        imageView=(ImageView)findViewById(R.id.image);
        upload=(ImageView)findViewById(R.id.upload);
        camera=(RelativeLayout)findViewById(R.id.camera);
        album=(RelativeLayout)findViewById(R.id.album);
        loadingView=(LoadingView)findViewById(R.id.loadView);

    }

    private void initListener(){//初始化各个事件

    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_case);
    }
}
