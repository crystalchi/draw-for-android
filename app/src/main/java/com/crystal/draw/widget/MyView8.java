package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 手指轨迹--写字
 * http://blog.csdn.net/harvic880925/article/details/50995587
 * Created by Administrator on 2016/10/17 0017.
 */

public class MyView8 extends View {

    private Path mPath;
    private float mPreX;
    private float mPreY;
    private Paint mPaint;

    public MyView8(Context context) {
        super(context);
        init();
    }

    public MyView8(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView8(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.moveTo(100, 300);
        mPath.rQuadTo(100, -100, 200, 0);
        mPath.rQuadTo(100, 100, 200, 0);
        canvas.drawPath(mPath, mPaint);
    }


    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
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
    }

    public void clear(){
        mPath.reset();
        postInvalidate();
    }*/

    /**
     * 单独把init方法写在构造方法中是为了防止频繁创建对象(这里的对象指paint)，
     * 对造成不断的内存回收，即频繁GC，在GC过程中，手机性能、内存各方面都会受到影响，表现出来就是会卡。
     */
    private void init(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
    }
}
