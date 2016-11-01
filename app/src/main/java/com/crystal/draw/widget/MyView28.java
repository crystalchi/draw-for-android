package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.crystal.draw.R;

/**
 * 放大镜
 * Created by Administrator on 2016/11/1 0001.
 */

public class MyView28 extends View{

    private Paint mPaint;
    private Bitmap mBgBitmap; //背景bitmap
    private Bitmap mDstBitmap;
    private Bitmap mSrcbitmap;

    private Point mTouchPoint = new Point();


    private Path mPath = new Path();
    private Matrix matrix = new Matrix();
    private Bitmap bitmap;
    //放大镜的半径
    private static final int RADIUS = 80;
    //放大倍数
    private static final int FACTOR = 2;
    private int mCurrentX, mCurrentY;

    public MyView28(Context context) {
        super(context);
        init();
    }

    public MyView28(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView28(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*mTouchPoint.set(200, 200);
        canvas.drawBitmap(mBgBitmap, 0, 0, mPaint);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        Canvas c2 = new Canvas(mSrcbitmap);
        mPaint.setColor(Color.TRANSPARENT);
        c2.drawCircle(mTouchPoint.x, mTouchPoint.y, 50, mPaint);
        canvas.drawBitmap(mSrcbitmap, 0, 0, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        Canvas c1 = new Canvas(mDstBitmap);
        mPaint.setColor(Color.GRAY);
        c1.drawRect(new Rect(0, 0, getWidth(), getHeight()), mPaint);
        canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);*/

        //底图
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        //剪切
        canvas.translate(mCurrentX - RADIUS, mCurrentY - RADIUS);
        canvas.clipPath(mPath);
        //画放大后的图
        canvas.translate(RADIUS-mCurrentX*FACTOR, RADIUS-mCurrentY*FACTOR);
        canvas.drawBitmap(mBgBitmap, matrix, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mTouchPoint.set((int) event.getX(), (int) event.getY());
                break;
        }*/
//        mTouchPoint.set((int) event.getX(), (int) event.getY());
        mCurrentX = (int) event.getX();
        mCurrentY = (int) event.getY();
        postInvalidate();
        return true;
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        mDstBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(), mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mSrcbitmap = Bitmap.createBitmap(mBgBitmap.getWidth(), mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        mPath.addCircle(RADIUS, RADIUS, RADIUS, Path.Direction.CW);
        matrix.setScale(FACTOR, FACTOR);
    }
}
