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
 * 贝赛尔弧线一
 * http://blog.csdn.net/harvic880925/article/details/50995587
 * Created by Administrator on 2016/10/17 0017.
 */

public class MyView6 extends View {

    public MyView6(Context context) {
        super(context);
    }

    public MyView6(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView6(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);

        //二阶贝赛尔弧线
        Path path = new Path();
        path.moveTo(100, 300);
        path.quadTo(200, 200, 300, 300);
        path.quadTo(400, 400, 500, 300);

        canvas.drawPath(path, paint);
    }

}
