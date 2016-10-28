package com.crystal.draw.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.crystal.draw.R;

/**
 * 贝塞尔曲线之水波波纹效果
 * http://blog.csdn.net/harvic880925/article/details/50995587
 * Created by Administrator on 2016/10/17 0017.
 */

public class MyView8 extends View {

    private Path mPath;
    private Paint mPaint;
    private int mItemWaveLength = 1000;
    private Context mContext;
    private int dx;
    private int dy;
    private int originY = 800;

    public MyView8(Context context) {
        super(context);
        this.mContext = context;
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

        /*mPath.moveTo(100, 300);
        mPath.rQuadTo(100, -100, 200, 0);
        mPath.rQuadTo(100, 100, 200, 0);*/

        /*mPath.moveTo(100, 200);
        mPath.quadTo(200, 100, 300, 200);
        canvas.drawPath(mPath, mPaint);*/

        mPath.reset();
        int halfWaveLen = mItemWaveLength/2;
        mPath.moveTo(-mItemWaveLength + dx, originY - dy);
        /**
         * for循环的次数少，绘制的波动即曲线效果在屏幕之内。
         * 若次数之多，绘制的效果会现在在屏幕之外（右侧屏幕外）
         */
        for(int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength){
            mPath.rQuadTo(halfWaveLen / 2, -50, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, 50, halfWaveLen, 0);
        }
        /*for (int i = 0; i < 3; i++){
            mPath.rQuadTo(halfWaveLen / 2, -20, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, 20, halfWaveLen, 0);
        }*/
        //path闭合
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }



    /**
     * 单独把init方法写在构造方法中是为了防止频繁创建对象(这里的对象指paint)，
     * 对造成不断的内存回收，即频繁GC，在GC过程中，手机性能、内存各方面都会受到影响，表现出来就是会卡。
     */
    private void init(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mContext.getResources().getColor(R.color.colorWater));
        mPaint.setStrokeWidth(10);
    }

    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();


        ValueAnimator animator1 = ValueAnimator.ofInt(0, originY);
        animator1.setDuration(5000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dy = (int)animation.getAnimatedValue();
            }
        });
        animator1.start();
    }

}
