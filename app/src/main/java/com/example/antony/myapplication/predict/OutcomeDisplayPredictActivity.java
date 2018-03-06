package com.example.antony.myapplication.predict;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.data.Image;
import com.example.antony.myapplication.path.LocalCaseActivity;
import com.example.antony.myapplication.sign.MainActivity;
import com.example.antony.myapplication.util.ImageLayout;
import com.example.antony.myapplication.util.RoundImageView;
import com.example.antony.myapplication.util.UltimateBar;

public class OutcomeDisplayPredictActivity extends BaseActivity {

    /*控件对象*/
    private ImageView imageView;

    /*数据对象*/
    private Intent intent;
    private Image image;
    private String type;


    /*初始化控件*/

    /**
     * 初始化控件
     */
    private void initView(){

        imageView=(ImageView)findViewById(R.id.image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    /*初始化控件*/


    /*重写BaseActivity中的方法*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome_display_predict);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.light_red));
        initView();
        intent=getIntent();
        type=intent.getStringExtra("type");
        if(type.equals("online")){
            image=(Image)intent.getSerializableExtra("image");
            Glide.with(this).load(image.getUrl()).into(imageView);
        }

    }
     /*重写BaseActivity中的方法*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(OutcomeDisplayPredictActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
        return true;

    }
}
