package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.crystal.draw.R;

/**
 * 360浏览器下拉刷新拉伸效果
 * Created by Administrator on 2016/10/25 0025.
 */

public class MyView14 extends FrameLayout{

    private static final float DEFAULT_RADIUS = 60; //默认半径
    private float mRadius = DEFAULT_RADIUS;
    private Paint mPaint;
    private Path mPath;
    private PointF mStartPoint; //开始坐标
    private PointF mControllPoint; //贝塞尔曲线控制点坐标
    private boolean mTouch; //触摸标识
    private float speed = 100; //速度

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
        mStartPoint.set(getWidth() / 2, 100);
        if(mTouch){
            if(speed >= 0){
                speed -= 1; //速度从100开始递减1
                if(speed < 50){
                    speed = 50; //最小只能为50
                }
            }
            //随着speed的减小，圆的半径也会减小，控制点y坐标变大。当然贝塞尔曲线的开始点以及结束点也会变。因为这两点是在圆的切线上的点。
            calculatePath();
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius * speed / 100, mPaint);
            canvas.drawPath(mPath, mPaint);
        }else{
            if(speed != 100) {
                speed += 1; //手指放开时，speed从50递增1
                mControllPoint.y -= 4; //控制点y坐标变小。
                calculatePath();
                canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius * speed / 100, mPaint);
                canvas.drawPath(mPath, mPaint);
                postInvalidateDelayed(10);
            }else{
                canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius * speed / 100, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mTouch = true;
                break;
            case MotionEvent.ACTION_UP: //手指放开，恢复初始标识
                mTouch = false;
                mRadius = DEFAULT_RADIUS;
                break;
        }
        if(event.getY() - mStartPoint.y <= 400){ //最大间距不能超过400
            mControllPoint.set(getWidth() / 2, event.getY());
        }
        postInvalidate();
        return true;
    }

    private void calculatePath(){
        mPath.reset();
        mPath.moveTo(mStartPoint.x - mRadius * speed / 100, mStartPoint.y);
        mPath.quadTo(mControllPoint.x, mControllPoint.y, mStartPoint.x + mRadius * speed / 100, mStartPoint.y);
    }


    private void init(Context context){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.colorWater));

        mPath = new Path();

        mStartPoint = new PointF();
        mControllPoint = new PointF();

    }
}
