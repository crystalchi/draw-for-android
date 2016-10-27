package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * Paint之setXfermode基础二之圆形圆角图片
 * Created by Administrator on 2016/10/27 0027.
 */

public class MyView19 extends View {

    private Paint mPaint;
    private Bitmap dst;
    private Bitmap src;

    public MyView19(Context context) {
        super(context);
        init();
    }

    public MyView19(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView19(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        //画目标图
        canvas.drawBitmap(dst, 0, 0, mPaint);
        //设置图像混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //画源图
        canvas.drawBitmap(src, 0, 0, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerId);

    }

    private void init(){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        dst = BitmapFactory.decodeResource(getResources(), R.drawable.dog_shade);
        src = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
