package com.example.antony.myapplication.path;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.bean.PointSimple;
import com.example.antony.myapplication.data.Image;
import com.example.antony.myapplication.sign.MainActivity;
import com.example.antony.myapplication.train.OutcomeDisplayTrainActivity;
import com.example.antony.myapplication.util.ImageLayout;
import com.example.antony.myapplication.util.UltimateBar;
import com.example.antony.myapplication.util.ViewToBitmap;

import java.util.ArrayList;

public class OutcomeDisplayPathActivity extends BaseActivity implements OutcomeDisplayPathView {

    /*各个控件对象，例如：private TextView accountName*/
    private ImageLayout imageLayout;
    private RelativeLayout share;
    private RelativeLayout feedback;

    /*初始化*/
    private int windowWidth,windowHeight;
    private Image image;
    private Intent intent;
    private String type;


    /*初始化*/
    /**
     *初始化各个控件
     */
    private void initViews(){
        imageLayout=(ImageLayout)findViewById(R.id.image_layoutout);
        share=(RelativeLayout)findViewById(R.id.share);
        feedback=(RelativeLayout)findViewById(R.id.feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     *初始化各个事件
     */
    private void initListener(){
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "\n----来源于E-teach");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Share"));*/
                String path=ViewToBitmap.saveBitmap(imageLayout);
                Intent imageIntent = new Intent(Intent.ACTION_SEND);
                imageIntent.setType("image/jpeg");
                imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                startActivity(Intent.createChooser(imageIntent, "分享"));

            }
        });
        feedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(OutcomeDisplayPathActivity.this,"功能正在开发，敬请期待",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *其他初始化操作
     */
    private void initOthers(){
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
    }
    /*初始化*/


    /*重写BaseActivity中的方法*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome_display_path);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.light_red));
        initViews();
        initListener();
        intent=getIntent();
        type=intent.getStringExtra("type");
        if(type.equals("online")){
            initOthers();
        }

    }


    /*重写OutcomeDisplayView中的方法*/

     /*重写OutcomeDisplayView中的方法*/


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
                Intent intent = new Intent(OutcomeDisplayPathActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
        return true;

    }
}
