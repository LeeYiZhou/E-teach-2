package com.example.antony.myapplication.path;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.antony.myapplication.BaseActivity;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.adapter.CaseImageAdapter;
import com.example.antony.myapplication.data.Image;

import java.util.ArrayList;
import java.util.List;

public class OnlineCaseActivity extends BaseActivity implements OnlineCaseView{

    //各个控件对象，例如：private TextView accountName
    private List<Image> caseImageList = new ArrayList<>();

    //自定义函数
    private void initViews(){//初始化各个控件
        initCaseImages(); // 仅测试用

        //获取recyclerView的实例
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_case_img);
        //RecyclerView用哪种布局，这里就建立哪种manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        CaseImageAdapter adapter = new CaseImageAdapter(caseImageList);
        recyclerView.setAdapter(adapter);

    }

    private void initListener(){//初始化各个事件

    }

    private void initOthers(){//其他初始化操作，例如pickerView、popupWindow

    }

    //重写BaseActivity中的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_case);
    }

    private void initCaseImages() { // 仅测试用
        caseImageList.add(new Image(R.mipmap.img_case_0));
        caseImageList.add(new Image(R.mipmap.img_case_1));
        caseImageList.add(new Image(R.mipmap.img_case_2));
        caseImageList.add(new Image(R.mipmap.img_case_3));
        caseImageList.add(new Image(R.mipmap.img_case_4));
        caseImageList.add(new Image(R.mipmap.img_case_5));
    }


    //重写OnlineCaseView中的方法
}
