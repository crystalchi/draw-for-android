package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Matrix矩阵API详解
 * 参考：
 * http://biandroid.iteye.com/blog/1399462
 * http://www.gcssloop.com/customview/Matrix_Basic/
 * https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/%5B10%5DMatrix_Method.md
 * Created by Administrator on 2016/11/8 0008.
 */

public class MatrixImageView extends ImageView {

    private static final String TAG = MatrixImageView.class.getSimpleName();
    private Matrix mMatrix;
    private boolean mFirst = true;

    public MatrixImageView(Context context) {
      this(context, null);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*if(mFirst){ //加此标识防止多次调用draw方法
            RectF rectF = new RectF(200, 200, 500, 800); //初始rectF
            Log.d(TAG, "rectF before is " + rectF.toString());

            mMatrix.setScale(0.5f, 0.5f);
            mMatrix.mapRect(rectF); //根据当前mMatrix矩阵并在初始rectF的基础上计算出当前变换后的rectF
            Log.d(TAG, "rectF after is " + rectF.toString());
            mFirst = false;

            *//**
             * rectF before is RectF(200.0, 200.0, 500.0, 800.0)
             * rectF after is RectF(100.0, 100.0, 250.0, 400.0)
             *//*
        }*/




    }

    private void init(){
        mMatrix = new Matrix();
    }
}
