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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.crystal.draw.R;

/**
 * QQ红点消息气泡拉伸爆炸效果实现
 * 参考API：Java.lang.Math类  http://www.yiibai.com/javalang/java_lang_math.html
 * Created by Administrator on 2016/10/21 0021.
 */

public class MyView12 extends FrameLayout {

    private static final String TAG = MyView12.class.getSimpleName();
    private static final float DEFAULT_RADIUS = 20;
    private float mRadius = DEFAULT_RADIUS;

    private Context mContext;
    private Paint mPaint;
    private Path mPath;
    private PointF mStartPoint; //第一个圆坐标
    private PointF mCurrentPoint; //手指移动位置的圆的坐标
    private boolean mTouch; //触摸标识
    private boolean isBomb; //爆炸标识

    private ImageView mBombImageView; //爆炸imageview
    private ImageView mTipImageView; //消息数字imageview

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
        Log.d(TAG, "mTouch is " + mTouch);
        //canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), mPaint, Canvas.ALL_SAVE_FLAG);
        if(isBomb || !mTouch){ //isBomb标识已经产生过爆炸效果，不可再次拉伸。
            //默认开始将mTipImageView的中心放在固定位置
            mTipImageView.setX(mStartPoint.x - mTipImageView.getWidth() / 2);
            mTipImageView.setY(mStartPoint.y - mTipImageView.getHeight() / 2);
        }else{
            calculatePath();
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius, mPaint);
            canvas.drawCircle(mCurrentPoint.x, mCurrentPoint.y, mRadius, mPaint);
            canvas.drawPath(mPath, mPaint);
            //将mTipImageView的中心放在当前手指位置
            mTipImageView.setX(mCurrentPoint.x - mTipImageView.getWidth() / 2);
            mTipImageView.setY(mCurrentPoint.y - mTipImageView.getHeight() / 2);
        }
        //canvas.restore();
        //绘制ViewGroup（即FrameLayout中的子控件）。
        // 这里指消息数字imageview,将这句放到最后是为了防止上面自己所画的图案覆盖掉子控件。
        //消息数字imageview必须覆盖在自己所画的图案效果上面，不然给用户看不到数字消息效果。
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int[] location = new int[2];
                mTipImageView.getLocationOnScreen(location); //获取消息数字imageview相对于屏幕的坐标
                Rect rect = new Rect();
                rect.left = location[0];
                rect.top = location[1];
                rect.right = location[0] + mTipImageView.getWidth();
                rect.bottom = location[1] + mTipImageView.getHeight();
                if(rect.contains((int) event.getRawX(), (int) event.getRawY())){ //消息数字imageview在手指触摸范围内，代表已经触摸到。
                    mTouch = true; //标识消息数字imageview已经触摸到
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouch = false; //手指放开还原
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
        double angle = Math.atan(dy / dx); //反正切求正切的角度
        double sinValue = Math.sin(angle);
        double cosValue = Math.cos(angle);
        float xValue = (float) (mRadius *  sinValue);
        float yValue = (float) (mRadius * cosValue);

        float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); //勾股定律开平方根
        mRadius = -distance / 20 + DEFAULT_RADIUS;
        if(mRadius < 9){ //拉伸长度达到一定长度时改变半径，半径小于9，产生爆炸效果。
            isBomb = true; //标识已爆炸
            mTouch = false; //设置false，不再画圆以及产生的贝塞尔曲线效果
            mTipImageView.setVisibility(View.GONE);
            mBombImageView.setX(mCurrentPoint.x - mTipImageView.getWidth() / 2);
            mBombImageView.setY(mCurrentPoint.y - mTipImageView.getWidth() / 2);
            mBombImageView.setVisibility(View.VISIBLE);
            ((AnimationDrawable)mBombImageView.getDrawable()).start(); //直接开启逐帧动画
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
        mBombImageView.setImageResource(R.drawable.bomb_anim); //设置逐帧动画
        mBombImageView.setVisibility(View.GONE); //爆炸imageview隐藏

        mTipImageView = new ImageView(context);
        mTipImageView.setLayoutParams(layoutParams);
        mTipImageView.setImageResource(R.drawable.skin_tips_newmessage_ninetynine);

        addView(mTipImageView);
        addView(mBombImageView);
    }
}
