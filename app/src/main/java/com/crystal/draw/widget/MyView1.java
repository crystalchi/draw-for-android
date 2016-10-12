package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * http://blog.csdn.net/harvic880925/article/details/38875149
 * Created by Administrator on 2016/10/12 0012.
 */

public class MyView1 extends View{

    public MyView1(Context context) {
        super(context);
    }

    public MyView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(30);
        paint.setColor(Color.RED);
        paint.setTextSize(100);

        //canvas.drawRGB(255, 255, 255); //设置画布背景
        //canvas.drawCircle(150, 150, 100, paint);




        //canvas.drawPoint(100, 100, paint);

        //canvas.drawCircle(200, 200, 50, paint);

        //canvas.drawOval(10, 10, 110, 110, paint);

        paint.setShadowLayer(10, 10, 10, Color.CYAN); //设置画笔阴影
        canvas.drawText("晴依小雨", 5, getHeight() / 2, paint);
    }
}
