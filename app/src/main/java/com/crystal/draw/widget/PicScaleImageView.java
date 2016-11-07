package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class PicScaleImageView extends ImageView implements
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = PicScaleImageView.class.getSimpleName();
    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix mMatrix;

    public static final float SCALE_MAX = 4.0f;
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    private boolean once = true;

    public PicScaleImageView(Context context) {
        this(context, null);
    }

    public PicScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
        this.mMatrix = new Matrix();
    }

    /**
     * 处理缩放事件
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d(TAG, "detector.getScaleFactor() is " + detector.getScaleFactor());
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        Log.d(TAG, "mMatrix is " + mMatrix.toShortString());
        if((scaleFactor > 1.0f && scale < SCALE_MAX)
                || (scale > initScale && scaleFactor < 1.0f)){ //放大scaleFactor大于1

            if(scaleFactor * scale > SCALE_MAX){
                scaleFactor = SCALE_MAX / scale;
            }
            if(scaleFactor * scale < initScale){
                scaleFactor = initScale / scale;
            }
            mMatrix.preScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mMatrix);
        }
        return true;
    }

    /**
     * 此处必须得返回true才能执行onScale缩放事件
     * @param detector
     * @return
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 把触摸事件交给ScaleGestureDetector缩放手势处理
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    private float getScale() {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void onGlobalLayout(){
        if (once){
            Drawable d = getDrawable();
            if (d == null)
                return;
            Log.e(TAG, d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
            int width = getWidth();
            int height = getHeight();
            // 拿到图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height){
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width){
                scale = height * 1.0f / dh;
            }
            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if (dw > width && dh > height){
                scale = Math.min(width * 1.0f /dw , height * 1.0f / dh);
            }
            initScale = scale;

            Log.e(TAG, "initScale = " + initScale);
            //注意混合变换的顺序
            mMatrix.postScale(scale, scale, getWidth() / 2,
                    getHeight() / 2);
            mMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);

            // 图片移动至屏幕中心
            setImageMatrix(mMatrix);
            once = false;
        }

    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale(){
        RectF rect = getMatrixRectF();
        Log.d(TAG, "rect.width() is " + rect.width());
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width){
            if (rect.left > 0){
                deltaX = -rect.left;
            }
            if (rect.right < width){
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height){
            if (rect.top > 0){
                deltaY = -rect.top;
            }
            if (rect.bottom < height){
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width){
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height){
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.e(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);
        mMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF(){
        Matrix matrix = mMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d){
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }
}
