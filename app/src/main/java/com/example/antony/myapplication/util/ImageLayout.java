package com.example.antony.myapplication.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.antony.myapplication.ActivityCollector;
import com.example.antony.myapplication.R;
import com.example.antony.myapplication.bean.Point;
import com.example.antony.myapplication.bean.PointSimple;

import java.util.ArrayList;

public class ImageLayout extends FrameLayout implements View.OnClickListener {

    ArrayList<PointSimple> points;

    FrameLayout layouPoints;

    RoundImageView imgBg;

    Context mContext;

    int windowWidth,windowHeight;

    ArrayList<Point> singlePointList=new ArrayList<>();

    public ImageLayout(Context context) {
        this(context, null);
    }

    public ImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {

        mContext = context;

        View imgPointLayout = inflate(context, R.layout.layout_imgview_point, this);

        imgBg = (RoundImageView) imgPointLayout.findViewById(R.id.imgBg);
        layouPoints = (FrameLayout) imgPointLayout.findViewById(R.id.layouPoints);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setImgBg(int width, int height,String imgUrl) {

        singlePointList=new ArrayList<>();
        this.windowWidth=width;
        this.windowHeight=width;

        ViewGroup.LayoutParams lp = imgBg.getLayoutParams();
        lp.width = width;
        lp.height = height;

        imgBg.setLayoutParams(lp);

        ViewGroup.LayoutParams lp1 = layouPoints.getLayoutParams();
        lp1.width = width;
        lp1.height = height;

        layouPoints.setLayoutParams(lp1);

        Glide.with(mContext).load(imgUrl).asBitmap().into(imgBg);

        addPoints(width, height);

    }

    public void setPoints(ArrayList<PointSimple> points) {

        this.points = points;
    }

    public void addPoint(int x,int y) {

        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_img_point, this, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imgPoint);
        LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.linear_layout);

        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin=x;
        layoutParams.topMargin=y;
        Point point=new Point();
        point.width=x;
        point.height=y;
        int size=singlePointList.size();
        linearLayout.setTag(size);
        singlePointList.add(point);
        linearLayout.setOnTouchListener(movingEventListener);
        layouPoints.addView(view, layoutParams);
    }

    private void addPoints(int width, int height) {

        layouPoints.removeAllViews();

        for (int i = 0; i < points.size(); i++) {

            double width_scale = points.get(i).width_scale;
            double height_scale = points.get(i).height_scale;


            LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_img_point, this, false);
            final ImageView imageView = (ImageView) view.findViewById(R.id.imgPoint);
            imageView.setTag(i);

            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.leftMargin = (int) (width * width_scale-50);
            layoutParams.topMargin = (int) (height * height_scale-50);

            imageView.setOnClickListener(this);

            layouPoints.addView(view, layoutParams);
        }
    }


    @Override
    public void onClick(View view) {
        int pos = (int) view.getTag();
        Toast.makeText(getContext(), "第" + (pos+1)+"步", Toast.LENGTH_SHORT).show();
    }

    private OnTouchListener movingEventListener = new OnTouchListener() {
        int lastX, lastY;
        int left,top;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();//相对于屏幕绝对坐标
                    lastY = (int) event.getRawY();//相对于屏幕绝对坐标
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;

                    /*view.getLeft()表示的是view左侧以其父View的左上角为原点的水平坐标位置 ;
                    view.getRight()表示的view右侧以其父View的左上角为原点的水平坐标位置 ;
                    view.getTop()表示的是view顶部以父View的左上角为原点的垂直坐标位置 ;
                    view.getBottom()表示的是view底部以父View的左上角为原点的垂直坐标位置 ;
                    view.getWidth()表示view宽度;
                    view.getHeight()表示view高度 ;*/
                    left = v.getLeft() + dx;
                    top = v.getTop() + dy;
                    int right = v.getRight() + dx;
                    int bottom = v.getBottom() + dy;
                    //设置不能出界
                    if (left < 0) {
                        left = 0;
                        right = left + v.getWidth();
                    }

                    if (right > windowWidth) {
                        right = windowWidth;
                        left = right - v.getWidth();
                    }

                    if (top < 0) {
                        top = 0;
                        bottom = top + v.getHeight();
                    }

                    if (bottom > windowHeight) {
                        bottom = windowHeight;
                        top = bottom - v.getHeight();
                    }

                    v.layout(left, top, right, bottom);

                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();

                    break;
                case MotionEvent.ACTION_UP:
                    int i=(int)v.getTag();
                    Point point=new Point();
                    point.width=left;
                    point.height=top;
                    singlePointList.set(i,point);
                    break;
            }
            return true;
        }
    };

    public int getSinglePointListSize(){
        return singlePointList.size()+1;
    }

    public void getSinglePointList(){
        ActivityCollector.pointList=singlePointList;
    }

}
