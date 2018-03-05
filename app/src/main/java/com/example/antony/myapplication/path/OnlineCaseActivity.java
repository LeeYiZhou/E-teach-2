package com.example.antony.myapplication.path;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.adapter.OnlineCaseAdapter;
import com.example.antony.myapplication.data.Image;

import java.util.ArrayList;
import java.util.List;

public class OnlineCaseActivity extends BaseActivity implements OnlineCaseView{

    /*各个控件对象，例如：private TextView accountName*/
    private RecyclerView recyclerView;
    private TextView localCase;
    /*各个数据*/
    private List<Image> imageList = new ArrayList<>();
    private String type;
    private OnlineCaseAdapter onlineCaseAdapter;
    private OnlineCasePresenter onlineCasePresenter;

    /*初始化方法*/
    /**
     * 初始化各个控件
     */
    private void initViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_case_img);
        localCase=(TextView)findViewById(R.id.local_case);
    }

    /**
     * 初始化各个事件
     */
    private void initListener(){
        localCase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(OnlineCaseActivity.this,LocalCaseActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView,newState);
                onlineCaseAdapter.changeMoreStatus(onlineCaseAdapter.LOADING_MORE);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==onlineCaseAdapter.getItemCount()) {
                    onlineCasePresenter.addImagesPresenter();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //LinearLayoutManager linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
                //lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                GridLayoutManager gridLayoutManager=(GridLayoutManager)recyclerView.getLayoutManager();
                lastVisibleItem=gridLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 其他初始化操作
     */
    private void initOthers(){
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        onlineCasePresenter=new OnlineCasePresenter(OnlineCaseActivity.this,OnlineCaseActivity.this);
        onlineCasePresenter.initImagesPresenter();
    }
    /*初始化方法*/


    /*重写BaseActivity中的方法*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_case);
        initViews();
        initListener();
        initOthers();

    }
    /*重写BaseActivity中的方法*/


    /*重写OnlineCaseView中的方法*/
    @Override
    public void showToastView(String str){
        Toast.makeText(OnlineCaseActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initAdapterView(List<Image> imageList){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        this.imageList=imageList;
        onlineCaseAdapter= new OnlineCaseAdapter(OnlineCaseActivity.this,imageList,type);
        recyclerView.setAdapter(onlineCaseAdapter);
    }

    @Override
    public void addAdapterView(List<Image> imageList){
        if(imageList.size()==0){
            onlineCaseAdapter.changeMoreStatus(onlineCaseAdapter.NO_LOAD_MORE);
            OnlineCaseActivity.this.showToastView("已经到底(≧▽≦)啦啦啦");
        }
        else {
            onlineCaseAdapter.addFooterItem(imageList);
            onlineCaseAdapter.changeMoreStatus(onlineCaseAdapter.PULLUP_LOAD_MORE);
        }

    }

    @Override
    public String showLastDateView(){
        String date=onlineCaseAdapter.getLastDate();
        Log.d("OnlineCaseActivity",date);
        return date;
    }
     /*重写OnlineCaseView中的方法*/


    /*其他方法*/

    /*其他方法*/

}
