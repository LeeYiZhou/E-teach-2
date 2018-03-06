package com.example.antony.myapplication.train;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.bean.Point;
import com.example.antony.myapplication.bean.PointSimple;
import com.example.antony.myapplication.data.Image;
import com.example.antony.myapplication.path.LocalCaseActivity;
import com.example.antony.myapplication.sign.MainActivity;
import com.example.antony.myapplication.util.ImageLayout;
import com.example.antony.myapplication.util.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class OutcomeDisplayTrainActivity extends BaseActivity {

    /*控件对象*/
    private ImageLayout imageLayout;

    /*数据对象*/
    private Image image;
    private int windowWidth;
    private int windowHeight;
    private List<Point> pointList;


    /*初始化*/

    /**
     * 初始化控价
     */
    private void initView(){

        imageLayout=(ImageLayout)findViewById(R.id.img_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initOthers(){
        pointList=ActivityCollector.pointList;
        Intent intent=getIntent();
        image=(Image)intent.getSerializableExtra("image");
        ArrayList<PointSimple> arrayList=new ArrayList<>();
        PointSimple pointSimple=new PointSimple();
        if((pointSimple=parseString(image.getPoint1()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint2()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint3()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint4()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint5()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint6()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint7()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint8()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint9()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint10()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint11()))!=null)
            arrayList.add(pointSimple);
        if((pointSimple=parseString(image.getPoint12()))!=null)
            arrayList.add(pointSimple);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        windowWidth=dm.widthPixels;
        windowHeight=dm.heightPixels;
        try{
            imageLayout.setPoints(arrayList);
            String url=image.getUrl();
            imageLayout.setImgBg(windowWidth,windowWidth,url);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        for(Point point:pointList){
            imageLayout.addPoint(point.width,point.height);
        }
    }
    /*初始化*/


    /*重写BaseActivity中的方法*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome_display_train);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.light_red));
        initView();
        initOthers();
    }
    /*重写BaseActivity中的方法*/


     /*其他方法*/

    /**
     * 把Image对象中点的坐标变成PointSimple
     * @param str Image对象中点的坐标
     * @return 所对应的PointSimple
     */
    PointSimple parseString(String str){
        PointSimple pointSimple=new PointSimple();
        try{
            String[] args=str.split("\\*");
            pointSimple.width_scale=Double.parseDouble(args[0]);
            pointSimple.height_scale=Double.parseDouble(args[1]);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return pointSimple;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(OutcomeDisplayTrainActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
        return true;

    }

}
