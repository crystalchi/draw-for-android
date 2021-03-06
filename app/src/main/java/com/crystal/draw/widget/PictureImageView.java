package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
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
 * Created by Administrator on 2016/11/11 0011.
 */

public class PictureImageView extends ImageView implements
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener{

    private static final String TAG = PictureImageView.class.getSimpleName();

    private static final float MAX_SCALE = 5.0f; //最大缩放scale
    private ScaleGestureDetector mScaleGestureDetector;
    private float[] matrixValues = new float[9];
    private Matrix mMatrix;
    private boolean mFirst = true;
    private boolean isMax;

    private PointF startPointF;
    private Matrix currentMatrix;

    private static final int DRAG_MODE = 1; //拖拽模式(translate)
    private static final int SCALE_MODE = 2; //缩放模式(scale)
    private int mode; //状态模式



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
        currentMatrix = new Matrix();
        startPointF = new PointF();
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //scaleFactor大于1.0f表示手势是扩大，小于1.0f表示手势是缩小。
        float scaleFactor = detector.getScaleFactor();
        float preScale = getPreScale();
        Log.d(TAG, "preScale is " + preScale);
        //图片放大
        if(scaleFactor > 1.0f){
            if(isMax){ //已经放大到最大
                if(preScale * scaleFactor > MAX_SCALE){
                    return false; //阻止不能放大的事件
                }else{
                    //放开，可缩小
                    isMax = false;
                    return true;
                }
            }
            //上一次的scale乘以最新的scale值大于最大scale，就计算缩放的最大的scale
            if(preScale * scaleFactor > MAX_SCALE){
                scaleFactor = MAX_SCALE / preScale;
                isMax = true; //标记最大
            }
        }
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
        int width = getWidth();
        int height = getHeight();
        RectF rectF = getMatrixRectF(mMatrix);
        float currentWidth = rectF.right - rectF.left;
        if(currentWidth < width){ //代表当前图片是缩放操作并且图片缩放的宽度小于屏幕宽度
            float scale = width * 1.0f / currentWidth;
            mMatrix.postScale(scale, scale, width / 2, height / 2);
            restoreDefaultScaleSize(); //恢复默认缩放大小
            setImageMatrix(mMatrix);
        }

    }

    /**
     * 图片缩放小于屏幕时，恢复到首次缩放的默认缩放大小。
     */
    private void restoreDefaultScaleSize(){
        RectF rectF = getMatrixRectF(mMatrix);
        int width = getWidth();
        int height = getHeight();
        float overX = 0.0f, overY = 0.0f;
        if(rectF.left > 0){
            overX = -rectF.left;
        }
        if(rectF.right < width){
            overX = width - rectF.right;
        }
        overY = 0.5f * height - rectF.bottom + 0.5f * rectF.height();
        mMatrix.postTranslate(overX, overY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //处理拖拽事件
        switch (event.getAction() & event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                //Log.d(TAG, "MotionEvent.ACTION_DOWN...");
                mode = DRAG_MODE;
                startPointF.set(event.getX(), event.getY());
                currentMatrix.set(getImageMatrix());
                Log.d(TAG, "currentMatrix is ACTION_DOWN " + currentMatrix.toShortString());
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d(TAG, "MotionEvent.ACTION_MOVE...");
                if(mode == DRAG_MODE){
                    float dx = event.getX() - startPointF.x;
                    float dy = event.getY() - startPointF.y;
                    Log.d(TAG, "currentMatrix is ACTION_MOVE " + currentMatrix.toShortString());
                    mMatrix.set(currentMatrix);
                    mMatrix.postTranslate(dx, dy);
                }
                if(mode == SCALE_MODE){
                    //将触摸事件交给缩放检测
                    boolean handle = mScaleGestureDetector.onTouchEvent(event);
                    Log.d(TAG, "handle is " + handle);
                    return handle;

                }
                break;
            case MotionEvent.ACTION_POINTER_UP: //当触点离开屏幕，但是屏幕上还有触点(手指)
                mode = 0;
                break;
            case MotionEvent.ACTION_POINTER_DOWN: //当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                mode = SCALE_MODE;
                break;
        }
        setImageMatrix(mMatrix);
        return true;

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

    /**
     * 获取上一次的缩放比率
     * @return
     */
    private float getPreScale(){
        mMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    /**
     * 获取当前图片matrix缩放的范围
     * @param m
     * @return
     */
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
