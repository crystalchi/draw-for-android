package com.crystal.draw.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.crystal.draw.R;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class MyView24 extends View {

    private int mWaveLength;
    private Bitmap dst;
    private Bitmap src;
    private Paint mPaint;
    private Paint mTransparentPaint;
    private int dx;

    public MyView24(Context context) {
        super(context);
        init(context);
    }

    public MyView24(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView24(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Canvas c = new Canvas(src);
        //清空bitmap
        c.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        c.drawRect(dst.getWidth() - dx,0,dst.getWidth(),dst.getHeight(),mTransparentPaint);

        //模式合成
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dst,0,0,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(src,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }

    public void startAnimation(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    private void init(Context context){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.color_c37e00));

        mTransparentPaint = new Paint();
        mTransparentPaint.setAntiAlias(true);
        mTransparentPaint.setStyle(Paint.Style.FILL);
        mTransparentPaint.setColor(Color.RED);

        dst = BitmapFactory.decodeResource(getResources(), R.drawable.heartmap);
        src = Bitmap.createBitmap(dst.getWidth(), dst.getHeight(), Bitmap.Config.ARGB_8888); //创建空白的bitmap

        mWaveLength = dst.getWidth();

        startAnimation();
    }
}
