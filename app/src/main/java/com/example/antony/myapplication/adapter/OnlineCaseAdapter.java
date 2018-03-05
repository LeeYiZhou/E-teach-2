package com.example.antony.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.data.Image;
import com.example.antony.myapplication.path.OutcomeDisplayPathActivity;
import com.example.antony.myapplication.predict.OutcomeDisplayPredictActivity;
import com.example.antony.myapplication.train.EditCaseActivity;
import com.example.antony.myapplication.util.RoundImageView;

import java.util.List;

/**
 * Created by 40178 on 2018/2/24.
 */

public class OnlineCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Image> mCaseImageList;
    private String type;
    private  LayoutInflater mInflater;
    private static final int TYPE_ITEM   = 0;
    private static final int TYPE_FOOTER = 1;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE     = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE     = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        View onLineView;
        ImageView caseImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);//初始化存放view的ViewHolder,让每个view只被加载一次
            onLineView=itemView;
            caseImageView = (ImageView) itemView.findViewById(R.id.case_image);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        View footerView;
        ProgressBar mPbLoad;
        TextView mTvLoadText;
        LinearLayout mLoadLayout;
        public FooterViewHolder(View view) {
            super(view);
            footerView=view;
            mPbLoad=(ProgressBar)view.findViewById(R.id.pbLoad);
            mTvLoadText=(TextView)view.findViewById(R.id.tvLoadText);
            mLoadLayout=(LinearLayout)view.findViewById(R.id.loadLayout);
        }
    }

    public OnlineCaseAdapter(Context context,List<Image> caseImageList,String type) {
        mContext=context;
        mCaseImageList = caseImageList;
        mInflater=LayoutInflater.from(mContext);
        this.type=type;
    }

    //必须重写下列3个方法
    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_ITEM){
            View view = mInflater.inflate(R.layout.adapter_on_line, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }else if(viewType==TYPE_FOOTER){
            View view=mInflater.inflate(R.layout.adapter_footview,parent,false);
            return new FooterViewHolder(view);
        }
       return null;
    }

    //对每个子项赋值，在每个子项滚动到屏幕内时执行
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final Image caseImage = mCaseImageList.get(position); //获取实例
            Glide.with(mContext).load(caseImage.getUrl()).asBitmap().into(itemViewHolder.caseImageView);
            itemViewHolder.caseImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(type.equals("path")){
                        Intent intent=new Intent(mContext, OutcomeDisplayPathActivity.class);
                        intent.putExtra("image",caseImage);
                        intent.putExtra("type","online");
                        mContext.startActivity(intent);
                    }
                    else if(type.equals("train")){
                        Intent intent=new Intent(mContext,EditCaseActivity.class);
                        intent.putExtra("image",caseImage);
                        intent.putExtra("type","online");
                        mContext.startActivity(intent);
                    }
                    else if(type.equals("predict")){
                        Intent intent=new Intent(mContext, OutcomeDisplayPredictActivity.class);
                        intent.putExtra("image",caseImage);
                        intent.putExtra("type","online");
                        mContext.startActivity(intent);
                    }

                }
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.mTvLoadText.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footerViewHolder.mTvLoadText.setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:
                    //隐藏加载更多
                    footerViewHolder.mTvLoadText.setText("已经到底了呜~~~~(>_<)~~~~");
                    footerViewHolder.mPbLoad.setVisibility(View.GONE);
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mCaseImageList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {
            //最后一个item设置为footerView
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 添加新的元素到列表
     * @param items
     */
    public void addFooterItem(List<Image> items) {
        mCaseImageList.addAll(items);
        notifyDataSetChanged();
    }
    /**
     * 更新加载更多状态
     * @param status
     */
    public void changeMoreStatus(int status){
        mLoadMoreStatus=status;
        notifyDataSetChanged();
    }

    public String getLastDate(){
        Image image=mCaseImageList.get(mCaseImageList.size()-1);
        String date=image.getCreatedAt();
        return date;
    }
}

