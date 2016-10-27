package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.crystal.draw.R;

/**
 * Paint之setXfermode基础二之橡皮擦效果
 * Created by Administrator on 2016/10/27 0027.
 */

public class MyView21 extends View{

    private Paint mPaint;
    private Bitmap dst, src;
    private float mPreX, mPreY;
    private Path mPath;


    public MyView21(Context context) {
        super(context);
        init();
    }

    public MyView21(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView21(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        //关键点
        //将手指轨迹画在bitmap上, 其实就是相当于把手指的轨迹画在了图片上
        Canvas c = new Canvas(dst);
        canvas.drawPath(mPath, mPaint);

        //然后直接将dst图片画在画布上,其实就是画目标图
        canvas.drawBitmap(dst, 0, 0, mPaint);
        //设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        //画源图
        canvas.drawBitmap(src, 0, 0, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();
                mPath.moveTo(mPreX, mPreY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX()) / 2;
                float endY = (mPreY + event.getY()) / 2;
                mPath.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                break;
        }
        postInvalidate();
        return true;
    }

    private void init(){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint  = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED); //不透明色为100%
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(45);

        src = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        //创建一个空白的bitmap
        dst = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

        mPath = new Path();
    }
}
