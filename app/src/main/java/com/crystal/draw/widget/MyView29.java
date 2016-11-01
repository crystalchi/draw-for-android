package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * Matrix矩阵基础一
 * http://www.cnblogs.com/plokmju/p/android_Matrix.html
 * Created by Administrator on 2016/11/1 0001.
 */

public class MyView29 extends View{

    private Bitmap mBitmap;
    private Paint mPaint;

    public MyView29(Context context) {
        super(context);
        init();
    }

    public MyView29(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView29(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        //matrix.setScale(3.0f, 3.0f);
        //matrix.setTranslate(100f, 200f);
        //matrix.setSkew(0.1f, 0.1f);
        //matrix.setRotate(90, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);

        matrix.postTranslate(100f, 200f);
        canvas.drawBitmap(mBitmap, matrix, mPaint);
    }

    private void init(){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gold);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
