package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

/**
 * http://blog.csdn.net/harvic880925/article/details/39056701
 * 假设用region1  去组合region2
 * DIFFERENCE(0), //最终区域为region1 与 region2不同的区域
 * INTERSECT(1), // 最终区域为region1 与 region2相交的区域
 * UNION(2),      //最终区域为region1 与 region2组合一起的区域
 * XOR(3),        //最终区域为region1 与 region2相交之外的区域
 * REVERSE_DIFFERENCE(4), //最终区域为region2 与 region1不同的区域
 * REPLACE(5); //最终区域为为region2的区域
 * Created by Administrator on 2016/10/13 0013.
 */
public class MyView3 extends View {

    private Paint mPaint;
    private Path mPath;

    public MyView3(Context context) {
        super(context);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(16);
        mPaint.setTextAlign(Paint.Align.RIGHT);

        mPath = new Path();
    }

    public MyView3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化画笔
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style./*FILL*/STROKE);
        paint.setStrokeWidth(2);

        //画一个矩形
        /*Region rgn = new Region(10,10,100,100);
        rgn.set(100, 100, 200, 200);*/

        //画一个半椭圆形
        //1.先画一个长椭圆形，再画一个与该长椭圆形有交际的半矩形（及长椭圆形的一半）
        /*Path intersectionPath = new Path();
        RectF rectF = new RectF(50, 50, 200, 500); //范围
        intersectionPath.addOval(rectF, Path.Direction.CW); //添加一个椭圆轮廓的路径
        Region rgn = new Region();
        //设置交集。intersectionPath可以理解为全集区域
        //setPath第二个参数可以理解为另一个区域, 调用setPath就是获取全集与第二个区域的交集。
        rgn.setPath(intersectionPath, new Region(50, 50, 200, 200));
        drawRegion(canvas, rgn, paint); //画出路径*/


        //画两矩形，取其交集
        /*Rect rect1 = new Rect(50, 50, 100, 200);
        canvas.drawRect(rect1, paint);
        Rect rect2 = new Rect(0, 100, 150, 150);
        canvas.drawRect(rect2, paint);
        //构造矩形区域
        Region region1 = new Region(rect1);
        Region region2 = new Region(rect2);
        region1.op(region2, Region.Op.INTERSECT);
        Paint intersectPaint = new Paint();
        intersectPaint.setAntiAlias(true);
        intersectPaint.setStyle(Paint.Style.STROKE);
        intersectPaint.setColor(Color.GREEN);
        drawRegion(canvas, region1, intersectPaint);*/


        //裁剪
        Paint mPaint = new Paint();


        canvas.save();
        canvas.translate(10, 10);
        canvas.clipRect(new Rect(100,100,200,200));//裁剪区域实际大小为50*50
        canvas.drawColor(Color.RED);
        canvas.restore();

        canvas.save();
        canvas.translate(10, 10);
        canvas.clipRect(new Rect(0, 0, 100, 100));
        canvas.drawColor(Color.BLACK);
        canvas.restore();


        //canvas.drawRect(new Rect(0,0,100,100), mPaint);//矩形实际大小为50*50

        //canvas.clipRegion(new Region(new Rect(300,300,400,400)));//裁剪区域实际大小为100*100
        //canvas.drawColor(Color.BLACK);

    }

    //这个函数不懂没关系，下面会细讲
    private void drawRegion(Canvas canvas,Region rgn,Paint paint)
    {
        RegionIterator iter = new RegionIterator(rgn); //矩形集（根据区域构建对应的矩形集）
        Rect r = new Rect();

        while (iter.next(r)) { //迭代获取每一个矩形
            //从矩形集中每下一个矩形，每取一个画一个矩形。将画笔Style设置成STROKE，就能看出区域Region是一个个矩形集组成的。
            canvas.drawRect(r, paint);
        }
    }


    private void drawScene(Canvas canvas) {
        canvas.clipRect(0, 0, 100, 100);

        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, 100, 100, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(30, 70, 30, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawText("Clipping", 100, 20, mPaint);
    }
}
