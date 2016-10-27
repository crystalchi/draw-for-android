package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class MyView17 extends View {

    private Paint mPaint;
    private Bitmap dst;
    private  Bitmap src;

    public MyView17(Context context) {
        super(context);
        init();
    }

    public MyView17(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView17(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        //画书架作为目标图
        canvas.drawBitmap(dst, 0, 0, mPaint);
        //设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        //画源图
        canvas.drawBitmap(src, 0, 0, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    private void init(){
        mPaint = new  Paint();
        mPaint.setAntiAlias(true);

        dst = BitmapFactory.decodeResource(getResources(), R.drawable.book_bg);
        src = BitmapFactory.decodeResource(getResources(), R.drawable.book_light);
    }
}
