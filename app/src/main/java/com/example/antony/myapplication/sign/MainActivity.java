package com.example.antony.myapplication.sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.homepage.HomepageActivity;
import com.example.antony.myapplication.path.OnlineCaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    //各个控件对象，例如：private TextView accountName
    private ImageView pathImg;
    private TextView pathTxt;
    private ImageView trainImg;
    private TextView trainTxt;
    private ImageView predictImg;
    private TextView predictTxt;
    private ImageView homepageImg;
    private TextView homepageTxt;

    //自定义函数
    private void initViews(){//初始化各个控件
        pathImg=(ImageView)findViewById(R.id.imageView);
        pathTxt=(TextView)findViewById(R.id.textView);
        trainImg=(ImageView)findViewById(R.id.imageView2);
        trainTxt=(TextView) findViewById(R.id.textView2);
        predictImg=(ImageView)findViewById(R.id.imageView3);
        predictTxt=(TextView)findViewById(R.id.textView3);
        homepageImg=(ImageView)findViewById(R.id.imageView4);
        homepageTxt=(TextView)findViewById(R.id.textView4);

    }

    private void initListener(){//初始化各个事件
        pathImg.setOnClickListener(this);
        pathTxt.setOnClickListener(this);
        trainImg.setOnClickListener(this);
        trainTxt.setOnClickListener(this);
        predictImg.setOnClickListener(this);
        predictTxt.setOnClickListener(this);
        homepageImg.setOnClickListener(this);
        homepageTxt.setOnClickListener(this);
    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListener();
    }

    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.imageView:
            case R.id.textView:
                Intent intent=new Intent(MainActivity.this, OnlineCaseActivity.class);
                intent.putExtra("type","path");
                startActivity(intent);
                break;
            case R.id.imageView2:
            case R.id.textView2:
                Intent intent2=new Intent(MainActivity.this,OnlineCaseActivity.class);
                intent2.putExtra("type","train");
                startActivity(intent2);
                break;
            case R.id.imageView3:
            case R.id.textView3:
                Intent intent3=new Intent(MainActivity.this,OnlineCaseActivity.class);
                intent3.putExtra("type","predict");
                startActivity(intent3);
                break;
            case R.id.imageView4:
            case R.id.textView4:
                Intent intent4=new Intent(MainActivity.this, HomepageActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }

    }
}
