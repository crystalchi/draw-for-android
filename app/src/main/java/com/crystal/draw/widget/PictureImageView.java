package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class PictureImageView extends ImageView implements
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener{

    private static final String TAG = PictureImageView.class.getSimpleName();

    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix mMatrix;
    private boolean mFirst = true;
    private float[] matrixValues = new float[9];

    public PictureImageView(Context context) {
        this(context, null);
    }

    public PictureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
        mMatrix = new Matrix();
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        //scaleFactor大于1.0f表示手势是扩大，小于1.0f表示手势是缩小。
        mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
        setImageMatrix(mMatrix);
        return true;
    }

    /**
     * 此处返回true，才能处理缩放手势事件
     * @param detector
     * @return
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Drawable drawable = getDrawable();
        if(null == drawable){
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int dWidth = drawable.getIntrinsicWidth();
        int dHeight = drawable.getIntrinsicHeight();
        RectF rectF = getMatrixRectF(mMatrix);
        float currentWidth = rectF.right - rectF.left;
        float currentHeight = rectF.bottom - rectF.top;
        if(currentWidth < width){
            float scale = width * 1.0f / currentWidth;
            mMatrix.postScale(scale, scale, width / 2, height / 2);
            disallowOverScreen();
            setImageMatrix(mMatrix);
        }

    }

    private void disallowOverScreen(){
        RectF rectF = getMatrixRectF(mMatrix);
        Drawable drawable = getDrawable();
        if(null == drawable){
            return;
        }
        int width = getWidth();
        int height = getHeight();
        float overX = 0.0f, overY = 0.0f;
        if(rectF.left > 0){
            overX = -rectF.left;
        }
        if(rectF.right < width){
            overX = width - rectF.right;
        }
        if(rectF.top > height / 2){
            overY = -(rectF.top - 0.5f * height  + 0.5f * rectF.height());
        }
        if(rectF.top < height / 2){
            overY = 0.5f * height - rectF.bottom + 0.5f * rectF.height();
        }
        mMatrix.postTranslate(overX, overY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onGlobalLayout() {
        if(mFirst){
            Drawable drawable = getDrawable();
            if(null == drawable){
                return;
            }
            int width = getWidth();
            int height = getHeight();
            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();

            float scale = 1.0f; //缩放比率(图片、屏幕宽高相等时默认不缩放)。

            //1.图片宽大于屏幕宽，高小于或等于屏幕高。
            if(dWidth > width && dHeight <= height){
                scale = width * 1.0f / dWidth;
            }

            //2.图片高大于屏幕高，宽小于或等于屏幕宽。
            if(dHeight > height && dWidth <= width){
                scale = height * 1.0f / dHeight;
            }

            //3.图片宽高都分别大于屏幕宽高。
            if(dWidth > width && dHeight > height){
                scale = Math.min(width * 1.0f / dWidth, height * 1.0f / dHeight);
            }

            //4.图片宽高都分别小于屏幕宽高。
            if(dWidth < width && dHeight < height){
                scale = Math.max(width * 1.0f / dWidth, height * 1.0f / dHeight);
            }

            mMatrix.postTranslate((width - dWidth) / 2, (height - dHeight) / 2);
            mMatrix.postScale(scale, scale, width / 2, height / 2);
            setImageMatrix(mMatrix);
            mFirst = false;
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private float getPreScale(){
        mMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    private RectF getMatrixRectF(Matrix m){
        Matrix matrix = m;
        Drawable drawable = getDrawable();
        RectF rectF = new RectF();
        if(null != drawable){
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }
}
