package com.crystal.draw.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2016/10/20 0020.
 */

public class MyView11 extends View {

    private static final String TAG = MyView11.class.getSimpleName();
    private Paint mPaint;
    private Paint mBallPaint;
    private Path mPath;
    private int mCenterY;
    private int currentY;
    private int mEndY;
    private int flySpeed = 100;
    private ValueAnimator animator;
    private final int radius = 40;

    public MyView11(Context context) {
        super(context);
        init();
    }

    public MyView11(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView11(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        if(mCenterY == 0){
            mCenterY = getHeight() / 2;
            mEndY = getHeight() / 8 * 7;
            mPath.moveTo(50, getHeight() / 2);
            mPath.quadTo(getWidth() / 2, mCenterY, getWidth() - 50, getHeight() / 2);
            canvas.drawPath(mPath, mPaint);

            startAnimator();
        }else{
            mPath.moveTo(50, getHeight() / 2);
            mPath.quadTo(getWidth() / 2,
                    mCenterY + (currentY - mCenterY) * flySpeed / 100/*currentY*/,
                    getWidth() - 50, getHeight() / 2);
            canvas.drawPath(mPath, mPaint);
            //Log.d(TAG, "currentY , mEndY is  " + currentY + " , " + mEndY);
            //Log.d(TAG, "flySpeed is " + flySpeed);
            canvas.drawCircle(getWidth() / 2,
                    flySpeed == 0 ? -100 : (mCenterY + (currentY - mCenterY) / 2) * flySpeed / 100 - 40/*mCenterY + (currentY - mCenterY) / 2*/,
                    radius, mBallPaint);



            int range = mCenterY + (currentY - mCenterY) / 2;
            int allRange = mCenterY + (mEndY - mCenterY) / 2;
            if(flySpeed > 0 && range > allRange - 3 && range <= allRange){ //到弧线中点范围内
                animator.cancel(); //cancel animator
                flySpeed -= 5;
                postInvalidateDelayed(10);
            }else{
                if(flySpeed == 0){
                    mCenterY = 0;
                    currentY = 0;
                    mEndY = 0;
                    postInvalidateDelayed(1000);
                }
                flySpeed = 100;
                //Log.d(TAG, "else flySpeed is " + flySpeed);
            }
        }
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);

        mBallPaint = new Paint();
        mBallPaint.setAntiAlias(true);
        mBallPaint.setColor(Color.GREEN);
        mBallPaint.setStrokeWidth(20);
        mBallPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
    }

    public void startAnimator(){
        animator = ValueAnimator.ofInt(mCenterY, mEndY);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
