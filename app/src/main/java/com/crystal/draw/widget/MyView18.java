package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * Paint之setXfermode基础二之twitter Logo的描边效果
 * Created by Administrator on 2016/10/27 0027.
 */

public class MyView18 extends View{

    private Bitmap dst;
    private Bitmap src;
    private Paint mPaint;

    public MyView18(Context context) {
        super(context);
        init();
    }

    public MyView18(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView18(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        int layerId = canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), mPaint, Canvas.ALL_SAVE_FLAG);

        //画目标图
        canvas.drawBitmap(dst, 0, 0, mPaint);
        //设置正片叠底图像混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        //画源图
        canvas.drawBitmap(src, 0, 0, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerId);

    }

    private void init(){
        src = BitmapFactory.decodeResource(getResources(), R.drawable.twiter_light);
        dst = BitmapFactory.decodeResource(getResources(), R.drawable.twiter_bg);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
