package com.crystal.draw.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * http://blog.csdn.net/wingichoy/article/details/50500479
 * Created by Administrator on 2016/10/19 0019.
 */

public class MyView10 extends View{

    private static final String TAG = MyView10.class.getSimpleName();

    private int mDoubleWaveLength = 400; //双峰波长
    private int mWaterLevel = 1000; //水位
    private Path mPath;
    private Paint mPaint;
    private Paint mBallPaint;
    private float mPreX;
    private float mPreY;
    private int dx;
    private int dy;

    private int mControX = 300;
    private int mControY = 300;
    private int mControLength = 1000;
    private int mContDy;

    private int startX = 300;
    private int startY = 100;
    private int endY = 300;
    private int currentY;

    private int bmobLineY;
    private boolean bmob = false;

    private int mCenterY = 600;
    private Point centerPoint = new Point();
    private Point endPoint = new Point();

    private boolean isBack;

    private int flySpeed = 100;

    public MyView10(Context context) {
        super(context);
        init();
    }

    public MyView10(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView10(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();
                mPath.moveTo(mPreX, mPreY);
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX()) / 2;
                float endY = (mPreY + event.getY()) / 2;
                mPath.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                postInvalidate();
                break;
        }
        return super.onTouchEvent(event);
    }*/


   /* @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

      *//*  mPath.moveTo(0, 300);
        mPath.quadTo(100, 200, 200, 300);
        mPath.quadTo(300, 400, 400, 300);*//*
        mPath.reset(); //每次重置（清空），否则效果会重叠
        mPath.moveTo(-mDoubleWaveLength + dx, mWaterLevel - dy);
        //包容在屏幕范围内外部分的波动一个400长度代表一个双峰波长
        int halfLength = mDoubleWaveLength / 2;
        for(int i = -mDoubleWaveLength; i <= getWidth() + mDoubleWaveLength; i += mDoubleWaveLength){
            mPath.rQuadTo(halfLength / 2, -20, halfLength, 0);
            mPath.rQuadTo(halfLength / 2, 20, halfLength, 0);
        }
        //闭合
        *//*mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();*//*
        canvas.drawColor(Color.GREEN);
        canvas.drawPath(mPath, mPaint);
    }*/


    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        *//*mPath.reset();
        mPath.moveTo(200, 200);
        mPath.quadTo(mControX, mControY, 400, 200);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(mControX, mControY, 20, mPaint);*//*

        *//*if(currentY == 0){
            drawCicleAnimator();
        }else{
            canvas.drawCircle(300, currentY, 20, mPaint);
        }
        canvas.drawLine(200, 200, 400, 200, mPaint);*//*

        int w2 = getWidth() / 2;
        int w50 = getWidth() - 50;
        int h2 = getHeight() / 2;
        Log.d(TAG, "currentY, getWidth() / 2, getWidth() - 50, getHeight() / 2 is " +
        currentY + " , " +  w2 + " , " + w50 + " , " +  h2);
        if(currentY == 0){
            bombAnimator();
        }else{
            mPath.reset();
            mPath.moveTo(50, getHeight() / 2);
            mPath.quadTo(getWidth() / 2, currentY, getWidth() - 50, getHeight() / 2);
            canvas.drawPath(mPath, mPaint);
            canvas.drawCircle(getWidth() / 2, currentY, 10, mBallPaint);
        }

    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if(isBack){
            mPath.reset();
            mPath.moveTo(50, mCenterY);
            mPath.quadTo(centerPoint.x, mCenterY +  (centerPoint.y - mCenterY) * flySpeed / 100, getWidth() - 50, mCenterY);
            canvas.drawPath(mPath, mPaint);

            if(flySpeed > 0){
                canvas.drawCircle(centerPoint.x, (mCenterY + (centerPoint.y - mCenterY) / 2) * flySpeed / 100, 40, mBallPaint);
                flySpeed -= 5;
                postInvalidateDelayed(10);
            }else{
                flySpeed = 100;
                isBack = false;
            }
        }else{
            centerPoint.x = getWidth() / 2;
            //canvas.drawCircle(centerPoint.x, centerPoint.y, 40, mBallPaint);
            mPath.reset();
            mPath.moveTo(50, mCenterY);
            mPath.quadTo(centerPoint.x, centerPoint.y, getWidth() - 50, mCenterY);
            canvas.drawPath(mPath, mPaint);

            int cy = (centerPoint.y - mCenterY) / 2 + mCenterY;
            canvas.drawCircle(centerPoint.x, cy, 40, mBallPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                return true;
            case MotionEvent.ACTION_MOVE:
                if(event.getY() > mCenterY){
                    centerPoint.y = (int) event.getY();
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                endPoint.x = (int) event.getX();
                endPoint.y = (int) event.getY();
                isBack = true;
                postInvalidate();
                break;
        }
        return true;
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mControX = (int) event.getX();
                mControY = (int) event.getY();
                postInvalidate();
                break;
        }
        return true;
    }*/


    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mControX = (int) event.getX();
                mControY = (int) event.getY();
                postInvalidate();
                //return true;
        }
        return true;
    }*/

    private void init(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);

        mBallPaint = new Paint();
        mBallPaint.setAntiAlias(true);
        mBallPaint.setStrokeWidth(10);
        mBallPaint.setStyle(Paint.Style.FILL);
        mBallPaint.setColor(Color.GREEN);

        centerPoint.y = mCenterY;
    }

    public void startAnimator(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mDoubleWaveLength);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(8000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

        ValueAnimator animator1 = ValueAnimator.ofInt(0, mWaterLevel);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setDuration(8000);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dy = (int) animation.getAnimatedValue();
            }
        });
        animator1.start();
    }

    public void changeAnimator(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mControLength);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(8000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mContDy = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    private ValueAnimator animator2;
    public void drawCicleAnimator(){
        animator2 = ValueAnimator.ofInt(startY, endY);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setDuration(8000);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator2.start();

    }

    private ValueAnimator animator3;

    public void drawBombLine(){
        animator3 = ValueAnimator.ofInt(200, mControY);
        animator3.setRepeatCount(ValueAnimator.INFINITE);
        animator3.setDuration(8000);
        animator3.setInterpolator(new LinearInterpolator());
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bmob = true;
                bmobLineY = (int) animation.getAnimatedValue();
            }
        });
        animator3.start();
    }

    public void bombAnimator(){
        mControY = getHeight() / 3 * 2;
        Log.d(TAG, "mControY , getHeight() / 2 is  " +  mControY + " , " + getHeight() / 2);
        ValueAnimator animator = ValueAnimator.ofInt(getHeight() / 2, mControY);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (int) animation.getAnimatedValue();
                postInvalidateDelayed(500);
            }
        });
        animator.start();
    }

}
