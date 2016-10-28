package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * setXfermode基础一
 * Created by Administrator on 2016/10/26 0026.
 */

public class MyView15 extends View{

    private Bitmap mBitmap;
    private int width = 400;
    private int height = 400;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint mPaint;

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



        int layerID = canvas.saveLayer(0,0,width*2,height*2,mPaint,Canvas.ALL_SAVE_FLAG);

        //绘制dst目标图
        canvas.drawBitmap(dstBmp, 0, 0, mPaint);
        //设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //绘制src源图
        canvas.drawBitmap(srcBmp, width / 3, height / 3, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);

    }

    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(Color.GREEN);
        c.drawRect(0, 0,w,h, p);
        /*c.drawRect(new RectF(0, 0, w, h), p);*/
        return bm;
    }

    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(Color.RED);
        c.drawRect(0, 0,w,h, p);
//        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    private void init(Context context){
        mPaint = new Paint();

        dstBmp = makeDst(width,height);
        srcBmp = makeSrc(width,height);
    }
}
