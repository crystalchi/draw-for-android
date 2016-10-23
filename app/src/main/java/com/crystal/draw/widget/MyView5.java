package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 *    计算各线的位置
 *    Paint.FontMetrics fm = paint.getFontMetrics();
 *    float ascent = baseLineY + fm.ascent; //当前绘制顶线坐标
 *    float descent = baseLineY + fm.descent; //当前绘制底线坐标
 *    float top = baseLineY + fm.top; //可绘制顶线坐标
 *    float bottom = baseLineY + fm.bottom; //可绘制底线坐标
 *
 * http://blog.csdn.net/harvic880925/article/details/50423762
 * Created by Administrator on 2016/10/14 0014.
 */

public class MyView5 extends View {

    public MyView5(Context context) {
        super(context);
    }

    public MyView5(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView5(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String text = "哦哦哦哦哦哦哦哦哦哦哦哦哦哦";

        //画基线draw baseline
        /*Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        canvas.drawLine(0, 200, 1000, 200, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        canvas.drawText(text, 0, 200, paint);*/

        //画各线的位置
        /*int baseLineY = 200;
        int baseLineX = 0 ;

        Paint paint = new Paint();
        //写文字
        paint.setColor(Color.GREEN);
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("harvic\'s blog", baseLineX, baseLineY, paint);

        //计算各线的位置
        Paint.FontMetrics fm = paint.getFontMetrics();
        float ascent = baseLineY + fm.ascent; //当前绘制顶线坐标
        float descent = baseLineY + fm.descent; //当前绘制底线坐标
        float top = baseLineY + fm.top; //可绘制顶线坐标
        float bottom = baseLineY + fm.bottom; //可绘制底线坐标

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //画top
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        //画ascent
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascent, 3000, ascent, paint);

        //画descent
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descent, 3000, descent, paint);

        //画bottom
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom, 3000, bottom, paint);*/

        /*
        int baseLineX = 0;
        int baseLineY = 200;

        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);

        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int top = baseLineY + fontMetricsInt.top;
        int bottom = baseLineY + fontMetricsInt.bottom;
        int width = (int) paint.measureText(text);
        Rect rect = new Rect(baseLineX, top, baseLineX + width, bottom);
        paint.setColor(Color.RED);
        canvas.drawRect(rect, paint);

        Rect minRect = new Rect();
        paint.getTextBounds(text, 0 , text.length(), minRect);
        minRect.top = minRect.top + baseLineY;
        minRect.bottom = minRect.bottom + baseLineY;
        paint.setColor(Color.GREEN);
        canvas.drawRect(minRect, paint);

        paint.setColor(Color.BLACK);
        canvas.drawText(text, baseLineX, baseLineY, paint);*/



        /*//给定左上顶点画图
        int top = 200;
        int baseLineX = 0;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(40);
        paint.setFakeBoldText(true);
        paint.setStrokeWidth(5);

        //画top线
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, top, 5000, top, paint);


        //根据top计算出基线位置
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        //fontMetricsInt.top = top - baseLineY;
        int baseLineY = top - fontMetricsInt.top;

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 5000, baseLineY, paint);

        //写文本
        paint.setColor(Color.BLACK);
        canvas.drawText(text, baseLineX, baseLineY, paint);*/



        //给定中间线画图
        /**
         * 推导：
         * A：top线到center线的距离
         * C：bottom线到center线的距离
         * B：基线到center线的距离
         *
         * A = C = (bottom - top) / 2;
         * bottom = baseLineY + fontMetricsInt.bottom;
         * top = baseLineY + fontMetricsInt.top;
         * A = C = (baseLineY + fontMetricsInt.bottom - baseLineY - fontMetricsInt.top) / 2;
         * A = C = (fontMetricsInt.bottom - fontMetricsInt.top) / 2;
         *
         * B = C - (bottom - baseLineY);
         * B = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - (baseLineY + fontMetricsInt.bottom - baseLineY);
         * B = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
         * baseLineY = B + center;
         * baseLineY = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom + center;
         */
        int center = 200;
        int baseLineX = 0;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setTextSize(40);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.LEFT);



        //计算baseLine基线
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int baseLineY = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom + center;

        //画基线
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, baseLineY, 5000, baseLineY, paint);

        //写文本
        paint.setColor(Color.BLACK);
        canvas.drawText(text, baseLineX, baseLineY, paint);

        //画center线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, center, 5000, center, paint);

    }
}
