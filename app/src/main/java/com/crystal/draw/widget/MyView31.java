package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * paint.setXfermode之DST_IN模式加深理解
 * Created by Administrator on 2016/11/3 0003.
 */

public class MyView31 extends View{

    private Paint mPaint;
    private Context mContext;
    private Bitmap mDstBitmap; //目标图
    private Bitmap mSrcBitmap; //源图
    private int w = 600;
    private int h = 600;

    public MyView31(Context context) {
        super(context);
        init(context);
    }

    public MyView31(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView31(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建全新透明的bitmap画布图层
        int layerId = canvas.saveLayer(0, 0, w, h, mPaint, Canvas.ALL_SAVE_FLAG);
        //画目标图
        canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);
        //设置DST_IT模式的混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画源图
        canvas.drawBitmap(mSrcBitmap, w / 3, h / 3, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);
        //返回上一层
        canvas.restoreToCount(layerId);
    }


    /**
     * 初始化
     * @param context
     */
    private void init(Context context){
        mContext = context;
        mPaint = new Paint();

        //创建Dst目标图
        mDstBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); //创建画布
        Canvas c = new Canvas(mDstBitmap); //创建画板
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(context.getResources().getColor(R.color.color_c37e00));
        c.drawRect(new Rect(0, 0, 600, 600), p);

        //创建Src源图
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.text);
    }

    /*
        总结：
        DST_IN模式公式：DST_IN [Sa * Da, Sa * Dc].
        此模式就是直接显示目标图(源图与目标图相交，源图未相交部分不显示)。
        当目标图与源图相交，相交的目标图部分会根据源图的Sa(透明度)的值有相应的变化。
        当源图的Sa透明度值为0(完全透明)时，相交的目标图部分会变为空白像素。当源图的Sa透明度为为1(完全不透明)时，
        相交的目标图部分还是以目标图本身的色值为主作为显示。

        注：本案例的效果其实就是在使用DST_IN的模式下，是一种“镂空”的效果。
        如果对这个结论分析的语义还是无法彻底理解，建议大家可以运行本案例程序看看实质的效果。
        当然还是希望大家可以多写一些使用DST_IN模式的demo，来加深自己的对此模式的理解。
        我也是写了这个demo，才对DST_IN有了深入彻底的理解，但是个人的体会是多写几个使用此模式不同的demo。
        这样对此模式会比较容易更好的理解。

        对了最重要的是对照这个公式去理解，对其中的每个参数的含义也需要弄明白。

        四个参数的含义：
        Sa全称为Source alpha表示源图的Alpha通道；
        Sc全称为Source color表示源图的颜色；
        Da全称为Destination alpha表示目标图的Alpha通道；
        Dc全称为Destination color表示目标图的颜色.
    */

}
