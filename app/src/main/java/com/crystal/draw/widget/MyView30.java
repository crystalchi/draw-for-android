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

        /*mMatrix.reset();
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/



        //skew错切 x方向
        /*mMatrix.setSkew(1.0f, 0);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/

        //skew错切 y方向
        /*mMatrix.setSkew(0, 0.5f);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/




        //前乘、后乘选择实现复合效果，setXXX系列函数每次调用就是清空之前的效果，所以要想实现复合效果。
        //就要选择前乘、后乘来实现符合我们需求的效果。可以看看一下几个例子。
        //在复合效果中，需要理解前乘、后乘的关系才能合适地选择前乘、后乘或者前乘后乘同时选择。
        //以便能到达我们所需要的效果。
        /*以下程序，//mMatrix注释标记均为不合适的选择*/

        /*1 . //先平移，再缩放。此处效果需要首先要以setTranslate为前提。然后再依据这个前提选择前乘还是后乘。
        mMatrix.setTranslate(100, 1000);
        mMatrix.preScale(0.5f, 0.5f); //前乘
        //mMatrix.postScale(0.5f, 0.5f); //后乘
        canvas.drawBitmap(mBitmap, mMatrix, null);*/


        /*2. //先缩放，再平移。此处效果首先以setScale为前提。然后再根据这个前提选择前乘还是后乘。
        mMatrix.setScale(0.5f, 0.5f);
        mMatrix.postTranslate(100, 1000);
        //mMatrix.preTranslate(100, 1000);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/


        /*3. //先由初始位置平移，再缩放，最后平移回初始位置
        mMatrix.setTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.postTranslate(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);*/
        //总结：根据上面关于前乘、后乘的三个效果，最好的方式还是列出公式然后一步一步进行推算，最终选择合适的前乘后乘。


        //setPolyToPoly：

        //
    }

    private void init(){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gold);
        mMatrix = new Matrix();
    }
}
