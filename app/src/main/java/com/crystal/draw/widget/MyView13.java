package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.crystal.draw.R;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class MyView13 extends FrameLayout{

    private static final int DEFAULT_RADIUS = 20;
    private int mRadius = DEFAULT_RADIUS;
    private Paint mPaint;
    private boolean mTouch;
    private PointF mStartPoint;
    private PointF mCurrentPoint;
    private Path mPath;
    private ImageView mBubbleImageView; //消息泡泡imageview

    public MyView13(Context context) {
        super(context);
        init(context);
    }

    public MyView13(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView13(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), mPaint, Canvas.ALL_SAVE_FLAG);
        if(mTouch){
            mBubbleImageView.setX(mCurrentPoint.x - mBubbleImageView.getWidth() / 2);
            mBubbleImageView.setY(mCurrentPoint.y - mBubbleImageView.getHeight() / 2);
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius, mPaint);
            canvas.drawCircle(mCurrentPoint.x, mCurrentPoint.y, mRadius, mPaint);
            calculatePath();
            canvas.drawPath(mPath, mPaint);
        }else{
            mBubbleImageView.setX(mStartPoint.x - mBubbleImageView.getWidth() / 2);
            mBubbleImageView.setY(mStartPoint.y - mBubbleImageView.getHeight() / 2);
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius, mPaint);
        }
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(isContains(event))
                    mTouch = true;
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
     * 计算path，贝塞尔曲线
     */
    private void calculatePath(){
        double angle = Math.atan((mCurrentPoint.y - mStartPoint.y) / (mCurrentPoint.x - mStartPoint.x));
        double sinValue = Math.sin(angle);
        double cosValue = Math.cos(angle);
        float xValue = (float) (mRadius * sinValue);
        float yValue = (float) (mRadius * cosValue);

        float x1 = mStartPoint.x - xValue;
        float y1 = mStartPoint.y + yValue;

        float x2 = mCurrentPoint.x - xValue;
        float y2 = mCurrentPoint.y + yValue;

        float x3 = mCurrentPoint.x + xValue;
        float y3 = mCurrentPoint.y - yValue;

        float x4 = mStartPoint.x + xValue;
        float y4 = mStartPoint.y - yValue;

        float centerX = (mCurrentPoint.x + mStartPoint.x) / 2;
        float centerY = (mCurrentPoint.y + mStartPoint.y) / 2;


        //拼接
        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.quadTo(centerX, centerY, x2, y2);
        mPath.lineTo(x3, y3);
        mPath.quadTo(centerX, centerY, x4, y4);
        mPath.lineTo(x1, y1);
        mPath.close();

    }

    private void init(Context context){
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        mStartPoint = new PointF(100, 100);
        mCurrentPoint = new PointF();

        mPath = new Path();

        LayoutParams  layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mBubbleImageView = new ImageView(context);
        mBubbleImageView.setLayoutParams(layoutParams);
        mBubbleImageView.setImageResource(R.drawable.skin_tips_newmessage_ninetynine);

        addView(mBubbleImageView);

    }

    /**
     * 判断手指触点的坐标位置该消息泡泡imageview坐标范围内
     * @param event
     * @return
     */
    private boolean isContains(MotionEvent event){
        boolean isContains = false;
        int[] location = new int[2];
        mBubbleImageView.getLocationOnScreen(location);
        Rect rect = new Rect();
        rect.left = location[0];
        rect.top = location[1];
        rect.right = location[0] + mBubbleImageView.getWidth();
        rect.bottom = location[1] + mBubbleImageView.getHeight();
        if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
            isContains = true;
        }
        return isContains;
    }
}
