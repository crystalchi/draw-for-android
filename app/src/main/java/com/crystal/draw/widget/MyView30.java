package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * 关于Matrix前乘、后乘的深入理解
 * 参考：
 * http://blog.csdn.net/linmiansheng/article/details/18820599
 * http://blog.csdn.net/cquwentao/article/details/51445269
 * http://blog.csdn.net/qq_30379689/article/details/52768910
 * http://blog.csdn.net/linmiansheng/article/details/18801947
 * Created by Administrator on 2016/11/1 0001.
 */

public class MyView30 extends View {

    private Bitmap mBitmap;
    private Matrix mMatrix;

    public MyView30(Context context) {
        super(context);
        init();
    }

    public MyView30(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView30(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, null);

        /*mMatrix.reset();
        mMatrix.setTranslate(100, 1000);
        //mMatrix.preScale(0.5f, 0.5f);
        mMatrix.postScale(0.5f, 0.5f);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/



        //right
        /*mMatrix.reset();
        mMatrix.setScale(0.5f, 0.5f);
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/

        /*mMatrix.reset();
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/

        /*mMatrix.reset();
        mMatrix.postTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/

        /*mMatrix.reset();
        mMatrix.setTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/


        //no right
        /*mMatrix.reset();
        mMatrix.postTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.preTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/

        /*mMatrix.reset();
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.preScale(0.5f, 0.5f);
        mMatrix.preTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/

        /*mMatrix.reset();
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.preScale(0.5f, 0.5f);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/

        /*mMatrix.reset();
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.postTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/

        mMatrix.reset();
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }

    private void init(){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gold);
        mMatrix = new Matrix();
    }
}
