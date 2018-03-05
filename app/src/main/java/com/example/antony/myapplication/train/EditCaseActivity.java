package com.example.antony.myapplication.train;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.bean.Point;
import com.example.antony.myapplication.bean.PointSimple;
import com.example.antony.myapplication.data.Image;
import com.example.antony.myapplication.util.ImageLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class EditCaseActivity extends BaseActivity {

    /*各个控件对象，例如：private TextView accountName*/
    private ImageLayout imageLayout;
    private LinearLayout dot;
    private RelativeLayout cancel;
    private RelativeLayout confirm;

    /*各个其他对象*/
    private int windowWidth;
    private int windowHeight;
    private Image image;
    private Intent intent;
    private String type;

    /*初始化*/
    /**
     *初始化各个控价
     */
    private void initViews(){
        dot=(LinearLayout)findViewById(R.id.bottom_bar);
        imageLayout=(ImageLayout)findViewById(R.id.image_layoutout);
        cancel=(RelativeLayout)findViewById(R.id.cancel);
        confirm=(RelativeLayout)findViewById(R.id.confirm);
    }

    /**
     * 初始化各个事件
     */
    private void initListener(){
        imageLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(ActivityCollector.flag1==1){
                    int size=imageLayout.getSinglePointListSize();
                    if(size<12){
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                int x = (int) motionEvent.getX();//相对于ImageLayout
                                int y = (int) motionEvent.getY();//相对于ImageLayout
                                imageLayout.addPoint(x,y);
                                break;
                        }
                        ActivityCollector.flag1=0;
                    }
                    else{
                        Toast.makeText(EditCaseActivity.this,"唇腭裂有12个点,不能再多了",Toast.LENGTH_SHORT).show();
                        ActivityCollector.flag1=0;
                    }
                }
                return true;
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.flag1=1;
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageLayout.getSinglePointListSize()<12){
                    Toast.makeText(EditCaseActivity.this,"唇腭裂有12个点,目前有"
                            +imageLayout.getSinglePointListSize()+"个点",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent(EditCaseActivity.this,OutcomeDisplayTrainActivity.class);
                    intent.putExtra("image",image);
                    imageLayout.getSinglePointList();
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 其他初始化操作
     */
    private void initOthers(){
        image=(Image) intent.getSerializableExtra("image");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        windowWidth=dm.widthPixels;
        windowHeight=dm.heightPixels;
        try{
            ArrayList<PointSimple> arrayList=new ArrayList<>();
            String url=image.getUrl();
            imageLayout.setPoints(arrayList);
            imageLayout.setImgBg(windowWidth,windowWidth,url);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ActivityCollector.flag1=0;
    }
    /*初始化*/


    /*重写BaseActivity中的方法*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case);
        initViews();
        intent=getIntent();
        type=intent.getStringExtra("type");
        if(type.equals("online")){
            initListener();
            initOthers();
        }

    }
    /*重写BaseActivity中的方法*/

}
