package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * http://blog.csdn.net/harvic880925/article/details/38926877
 * Created by Administrator on 2016/10/12 0012.
 */

public class MyView2 extends View{

    public MyView2(Context context) {
        super(context);
    }

    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE); //描边
        paint.setStrokeWidth(15);

        /*Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(100, 300);
        path.lineTo(300, 300);
        path.lineTo(300, 100);
        path.close();
        canvas.drawPath(path, paint);*/

        RectF rectf = new RectF(100, 100, 300, 300);
        Path path = new Path();
        path.addRect(rectf, Path.Direction.CW); //顺时针
        //path.addRect(rectf, Path.Direction.CCW); //逆时针
        Paint textPaint = new Paint();
        textPaint.setTextSize(30);
        textPaint.setAntiAlias(true);
        canvas.drawPath(path, paint);
        canvas.drawTextOnPath("很多很多和很多很多很多很多", path, 0, -10, textPaint);

        RectF rectF2 = new RectF(100, 400, 400, 600);
        Path path1 = new Path();
        path1.addRoundRect(rectF2, 10, 10, Path.Direction.CW);
        canvas.drawPath(path1, paint);

        Path path2 = new Path();
        path2.addCircle(200, 800, 100, Path.Direction.CW);
        canvas.drawPath(path2, paint);

        Path path3 = new Path();
        RectF rect =  new RectF(0, 0, 240, 200);
        path3.addArc(rect, 0, 60);
        canvas.drawPath(path3, paint); //画弧
    }
}
