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

public class MyView23 extends View {

    private int mWaveLength = 1000;
    private int mWaveY = 300;
    private Bitmap dst;
    private Bitmap src;
    private Paint mPaint;
    private Path mPath;
    private int dx;

    public MyView23(Context context) {
        super(context);
        init(context);
    }

    public MyView23(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView23(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        //生成波纹path
        mPath.reset();
        int halfWaveLength = mWaveLength / 2;
        mPath.moveTo(-mWaveLength + dx, src.getHeight() / 2);
        for(int i = -mWaveLength; i <= getWidth() + mWaveLength; i += mWaveLength){
            mPath.rQuadTo(halfWaveLength / 2, -50, halfWaveLength, 0);
            mPath.rQuadTo(halfWaveLength / 2, 50, halfWaveLength, 0);
        }
        mPath.lineTo(src.getWidth(), src.getHeight());
        mPath.lineTo(0, src.getHeight());
        mPath.close();

        //将path画在空白bitmap上，即图片上
        Canvas c = new Canvas(dst);
        c.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR); //每次清空上一次在bitmap上画的path，不然重叠效果不好
        c.drawPath(mPath, mPaint);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(src,0,0,mPaint);
        //先画目标图
        canvas.drawBitmap(dst, 0, 0, mPaint);
        //设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画源图
        canvas.drawBitmap(src, 0, 0, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerId);



        //canvas.drawPath(mPath, mPaint);
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
        mPath = new Path();

        src = BitmapFactory.decodeResource(getResources(), R.drawable.test_text);
        dst = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888); //创建空白的bitmap

        startAnimation();
    }
}
