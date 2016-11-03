package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * setXfermode基础一
 * Created by Administrator on 2016/10/26 0026.
 */

public class MyView15 extends View{

    private Bitmap mBitmap;
    private int width = 600;
    private int height = 600;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint mPaint;
    private Context mContext;

    public MyView15(Context context) {
        super(context);
        init(context);
    }

    public MyView15(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView15(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        int layerID = canvas.saveLayer(0, 0, width * 2, height * 2,mPaint, Canvas.ALL_SAVE_FLAG);

        //绘制dst目标图
        canvas.drawBitmap(dstBmp, 0, 0, mPaint);
        //设置混合模式
        //设置DST_IN模式，是显示目标图，如果源图有空白像素，那目标图与空白像素相交的区域也会变为空白的。
        //DST_IN公式：[Sa * Da, Sa * Dc]，根据公式就知道，当源图有空白像素时，与空白像素相交的目标图区域也会变为空白的。
        //即当Sa透明度为0时，目标图的Da(透明度)和Dc(颜色值，也可以成为饱和度)也会为0。就是空白像素。
        //其实下面这种效果就是当源图Sa为0时，目标图相交的区域也就镂空的效果。
        //我也是慢慢理解到这一点的，每个人都有自己的理解方式，只是表达不同而已，但是原理是不变的。所以DST_IN模式需要大家自己好好理解。
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //绘制src源图
        canvas.drawBitmap(srcBmp, width / 3, height / 3, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);

    }

    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(mContext.getResources().getColor(R.color.color_c37e00));
        c.drawRect(0, 0,w,h, p);
        /*c.drawRect(new RectF(0, 0, w, h), p);*/
        return bm;
    }

    private Bitmap makeSrc(int w, int h) {
        /*Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(Color.WHITE);
        c.drawRect(0, 0,w,h, p);*/
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.text);
        return bm;
    }

    private void init(Context context){
        mPaint = new Paint();
        this.mContext = context;
        dstBmp = makeDst(width,height);
        srcBmp = makeSrc(width,height);
    }
}
