package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 掌上英雄联盟能力值分析效果
 * http://blog.csdn.net/as7210636/article/details/52692102
 * https://github.com/jiangzehui/polygonsview
 * Created by Administrator on 2016/11/28 0028.
 */
public class AnalysisOfHeroAbility extends View {

    private static final int DEFAULT_SIZE = 300;
    private int defaultSize;
    private int center; //中心点
    private Paint mCenterPaint;

    public AnalysisOfHeroAbility(Context context) {
        this(context, null);
    }

    public AnalysisOfHeroAbility(Context context, AttributeSet attrs) {
        super(context, attrs);
        defaultSize = dp_px(DEFAULT_SIZE);

        mCenterPaint = new Paint();
        mCenterPaint.setColor(Color.RED);
        mCenterPaint.setStrokeWidth(10);
        mCenterPaint.setAntiAlias(true);
        mCenterPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            width = Math.min(widthSize, defaultSize);
        }

        if(heightMode == MeasureSpec.AT_MOST){
            height = heightSize;
        }else{
            height = Math.min(heightSize, defaultSize);
        }
        center = width / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        center(canvas);
    }

    /**
     * 画中心线
     * @param canvas
     */
    private void center(Canvas canvas){
        canvas.save();
        float startY = getPaddingTop();
        float endY = center;
        for (int i = 0; i < 7; i++){
            canvas.drawLine(center, startY, center, endY, mCenterPaint);
            canvas.rotate(360 / 7 , center, center);
        }
        canvas.restore();
    }

    /**
     * dp转px
     *
     * @param values
     * @return
     */
    public int dp_px(int values) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }
}
