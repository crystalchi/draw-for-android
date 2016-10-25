package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class MyView14 extends FrameLayout{

    private static final float DEFAULT_RADIUS = 40;
    private float mRadius = DEFAULT_RADIUS;
    private Paint mPaint;
    private Path mPath;
    private PointF mStartPoint;
    private PointF mControllPoint;
    private boolean mTouch;
    private float changeMini = 1f;
    private float flySpeed = 100;

    public MyView14(Context context) {
        super(context);
        init(context);
    }

    public MyView14(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView14(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(mTouch){
            if(flySpeed >= 0){
                flySpeed -= 1;
                if(flySpeed < 50){
                    flySpeed = 50;
                }
            }

            calculatePath();
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius * flySpeed / 100, mPaint);
            canvas.drawPath(mPath, mPaint);
        }else{
            if(flySpeed != 100) {
                flySpeed += 1;
            }
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius * flySpeed / 100, mPaint);
            postInvalidateDelayed(10);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                mTouch = false;
                mRadius = DEFAULT_RADIUS;
                break;
        }
        if(event.getY() <= 350){
            mControllPoint.set(100, event.getY());
        }
        postInvalidate();
        return true;
    }

    private void calculatePath(){
        mPath.reset();
        mPath.moveTo(mStartPoint.x - mRadius * flySpeed / 100, mStartPoint.y);
        mPath.quadTo(mControllPoint.x, mControllPoint.y, mStartPoint.x + mRadius * flySpeed / 100, mStartPoint.y);
    }


    private void init(Context context){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);

        mPath = new Path();

        mStartPoint = new PointF(100, 100);
        mControllPoint = new PointF();

    }
}
