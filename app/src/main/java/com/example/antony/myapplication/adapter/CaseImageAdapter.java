package com.example.antony.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.antony.myapplication.R;
import com.example.antony.myapplication.data.Image;
import com.example.antony.myapplication.util.RoundImageView;

import java.util.List;

/**
 * Created by 40178 on 2018/2/24.
 */

public class CaseImageAdapter extends RecyclerView.Adapter<CaseImageAdapter.ViewHolder> {

    private List<Image> mCaseImageList;

    //继承RecyclerView中的ViewHolder，并根据自己的情况加上内容
    //ViewHolder中存放了布局子项中的各个控件的实例 ，但是没有对每个子项的控件的内容赋具体值实例化
    static class ViewHolder extends RecyclerView.ViewHolder {
        RoundImageView caseImageView;

        public ViewHolder(View itemView) {
            super(itemView);//初始化存放view的ViewHolder,让每个view只被加载一次
            caseImageView = (RoundImageView) itemView.findViewById(R.id.case_image);
        }
    }

    public CaseImageAdapter(List<Image> caseImageList) {
        mCaseImageList = caseImageList;
    }

    //必须重写下列3个方法
    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_case_img_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //对每个子项赋值，在每个子项滚动到屏幕内时执行
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image caseImage = mCaseImageList.get(position); //获取实例
        holder.caseImageView.setImageResource(caseImage.getImageId());
    }

    @Override
    public int getItemCount() {
        return mCaseImageList.size();
    }
}

