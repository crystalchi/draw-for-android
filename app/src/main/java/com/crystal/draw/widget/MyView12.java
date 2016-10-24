package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.crystal.draw.R;

/**
 * QQ红点拖动删除效果实现
 * Created by Administrator on 2016/10/21 0021.
 */

public class MyView12 extends FrameLayout {

    private static final float DEFAULT_RADIUS = 20;
    private float mRadius = DEFAULT_RADIUS;

    private Context mContext;
    private Paint mPaint;
    private Path mPath;
    private PointF mStartPoint;
    private PointF mCurrentPoint;
    private boolean mTouch;
    private boolean isAnimStart;

    private ImageView mBombImageView;
    private ImageView mTipImageView;

    public MyView12(Context context) {
        super(context);
        init(context);
    }

    public MyView12(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView12(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), mPaint, Canvas.ALL_SAVE_FLAG);
        if(mTouch){
            calculatePath();
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius, mPaint);
            canvas.drawCircle(mCurrentPoint.x, mCurrentPoint.y, mRadius, mPaint);
            canvas.drawPath(mPath, mPaint);
            //将mTipImageView的中心放在当前手指位置
            mTipImageView.setX(mCurrentPoint.x - mTipImageView.getWidth() / 2);
            mTipImageView.setY(mCurrentPoint.y - mTipImageView.getHeight() / 2);
        }else{
            //默认开始将mTipImageView的中心放在固定位置
            mTipImageView.setX(mStartPoint.x - mTipImageView.getWidth() / 2);
            mTipImageView.setY(mStartPoint.y - mTipImageView.getHeight() / 2);
        }
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int[] location = new int[2];
                mTipImageView.getLocationOnScreen(location);
                Rect rect = new Rect();
                rect.left = location[0];
                rect.top = location[1];
                rect.right = location[0] + mTipImageView.getWidth();
                rect.bottom = location[1] + mTipImageView.getHeight();
                if(rect.contains((int) event.getRawX(), (int) event.getRawY())){
                    mTouch = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouch = false;
                break;
        }
        mCurrentPoint.set(event.getX(), event.getY());
        postInvalidate();
        return true;
    }

    /**
     * 计算两个圆四个切线点坐标并连接path
     */
    private void calculatePath(){
        float dy = mCurrentPoint.y - mStartPoint.y;
        float dx = mCurrentPoint.x - mStartPoint.x;
        double angle = Math.atan(dy / dx);
        double sinValue = Math.sin(angle);
        double cosValue = Math.cos(angle);
        float xValue = (float) (mRadius *  sinValue);
        float yValue = (float) (mRadius * cosValue);

        float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        mRadius = -distance / 15 + DEFAULT_RADIUS;
        if(mRadius < 9){
            isAnimStart = true;
            mTouch = false;
            mTipImageView.setVisibility(View.GONE);
            mBombImageView.setX(mStartPoint.x - mTipImageView.getWidth() / 2);
            mBombImageView.setY(mStartPoint.y - mTipImageView.getWidth() / 2);
            mBombImageView.setVisibility(View.VISIBLE);
            ((AnimationDrawable)mBombImageView.getDrawable()).start();
        }

        //根据角度以及三角函数定律计算出四个切点的坐标
        float x1 = mStartPoint.x - xValue;
        float y1 = mStartPoint.y + yValue;

        float x2 = mCurrentPoint.x - xValue;
        float y2 = mCurrentPoint.y + yValue;

        float x3 = mCurrentPoint.x + xValue;
        float y3 = mCurrentPoint.y - yValue;

        float x4 = mStartPoint.x + xValue;
        float y4 = mStartPoint.y - yValue;

        //两圆心连接的中点坐标
        float centerX = (mCurrentPoint.x + mStartPoint.x) / 2;
        float centerY = (mCurrentPoint.y + mStartPoint.y) / 2;

        //拼接path
        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.quadTo(centerX, centerY, x2, y2);
        mPath.lineTo(x3, y3);
        mPath.quadTo(centerX, centerY, x4, y4);
        mPath.lineTo(x1, y1);
        mPath.close();


    }

    private void init(Context context){
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();

        mStartPoint = new PointF(100, 100);
        mCurrentPoint = new PointF();

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBombImageView = new ImageView(context);
        mBombImageView.setLayoutParams(layoutParams);
        mBombImageView.setImageResource(R.drawable.bomb_anim);
        mBombImageView.setVisibility(View.GONE);

        mTipImageView = new ImageView(context);
        mTipImageView.setLayoutParams(layoutParams);
        mTipImageView.setImageResource(R.drawable.skin_tips_newmessage_ninetynine);

        addView(mTipImageView);
        addView(mBombImageView);
    }
}
