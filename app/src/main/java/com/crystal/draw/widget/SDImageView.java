package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class SDImageView extends ImageView implements
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener{

    private static final String TAG = SDImageView.class.getSimpleName();

    private static final float MAX_SCALE = 5.0f; //最大缩放scale
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private float[] matrixValues = new float[9];
    private Matrix mMatrix;
    private boolean mFirst = true;
    private boolean isMax;

    private PointF startPointF;
    private Matrix currentMatrix;

    private boolean doubleClick = false; //双击标识
    private Matrix mMinMatrix; //记录图片的最小缩放的范围
    private float tempScale; //记录双击放大缩小的scale
    private float initScale = 1.0f; //初始化缩放比率(默认)


    public SDImageView(Context context) {
        this(context, null);
    }

    public SDImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                currentMatrix.set(getImageMatrix());
                mMatrix.set(currentMatrix);
                mMatrix.postTranslate(-distanceX, -distanceY);
                disallowLeaveScreenBound();
                setImageMatrix(mMatrix);
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(!doubleClick){ //放大
                    tempScale = changeBiggerByDoubleTap();
                    doubleClick = true;
                }else{ //缩小
                    tempScale = initScale;
                    doubleClick = false;
                }
                postDelayed(new SDImageView.ScaleRunnable(tempScale, e.getX(), e.getY()), 30);
                return true;
            }
        });
        this.setOnTouchListener(this);
        mMatrix = new Matrix();
        currentMatrix = new Matrix();
        startPointF = new PointF();
        mMinMatrix = new Matrix();
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
        //单点触控
        mGestureDetector.onTouchEvent(event);
        //多点触控
        mScaleGestureDetector.onTouchEvent(event);
        return true;
        /*//处理拖拽事件
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //Log.d(TAG, "MotionEvent.ACTION_DOWN...");
                startPointF.set(event.getX(), event.getY());
                currentMatrix.set(getImageMatrix());
                Log.d(TAG, "currentMatrix is ACTION_DOWN " + currentMatrix.toShortString());
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d(TAG, "MotionEvent.ACTION_MOVE...");
                    float dx = event.getX() - startPointF.x;
                    float dy = event.getY() - startPointF.y;
                    Log.d(TAG, "currentMatrix is ACTION_MOVE " + currentMatrix.toShortString());
                    mMatrix.set(currentMatrix);
                    mMatrix.postTranslate(dx, dy);
                break;
        }
        setImageMatrix(mMatrix);
        return true;*/

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
            initScale = getPreScale(); //保存默认的最小缩放比率
            mMinMatrix.set(mMatrix);
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





    /**
     * 图片拖拽时不能离开屏幕的边界
     */
    private void disallowLeaveScreenBound(){
        RectF rectF = getMatrixRectF(mMatrix);
        float offsetX = 0.0f; //记录图片偏离屏幕的横坐标值
        float offsetY = 0.0f; //记录图片偏离屏幕的纵坐标值
        int width = getWidth(); //屏幕宽度
        int height = getHeight(); //屏幕高度

        if(rectF.width() >= width){
            if(rectF.left > 0){
                offsetX = - rectF.left;
            }
            if(rectF.right < width){
                offsetX = width - rectF.right;
            }
        }
        if(rectF.height() >= height){
            if(rectF.top > 0){
                offsetY = -rectF.top;
            }
            if(rectF.bottom < height){
                offsetY = height - rectF.bottom;
            }
        }
        mMatrix.postTranslate(offsetX, offsetY);
    }

    /**
     * 在初始默认的图片大小基础上变大显示满屏
     * @return
     */
    private float changeBiggerByDoubleTap(){
        RectF rectF = getMatrixRectF(mMinMatrix);
        float dwidth = rectF.right - rectF.left;
        float dheight = rectF.bottom - rectF.top;
        float width = getWidth();
        float height = getHeight();
        float scale = Math.max(width / dwidth, height / dheight);
        scale *= initScale;
        return scale;
    }

    private class ScaleRunnable implements Runnable{

        private float scale;
        private float x, y;

        public ScaleRunnable(float scale, float x, float y){
            this.scale = scale;
            this.x = x;
            this.y = y;
        }

        @Override
        public void run() {
            mMatrix.reset();
            mMatrix.postTranslate(getWidth() / 2 - x, getHeight() / 2 - y);
            mMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            controllPicRangeInScreen();
            //mCurrentMatrix.set(mMatrix);
            setImageMatrix(mMatrix);
        }
    }

    /**
     * 控制图片缩放后在屏幕中的范围
     */
    private void controllPicRangeInScreen(){
        RectF rectF = getMatrixRectF(mMatrix);
        float offsetX = 0.0f; //记录图片偏离屏幕的横坐标值
        float offsetY = 0.0f; //记录图片偏离屏幕的纵坐标值
        int width = getWidth(); //屏幕宽度
        int height = getHeight(); //屏幕高度

        //图片宽度大于等于屏幕宽度
        if(rectF.width() >= width){
            //图片左边偏离屏幕
            if(rectF.left > 0){
                offsetX = -rectF.left;
            }
            //图片右边偏离屏幕
            if(rectF.right < width){
                offsetX = width - rectF.right;
            }
        }
        //图片高度大于等于屏幕高度
        if(rectF.height() >= height){
            if(rectF.top > 0){
                offsetY = - rectF.top;
            }
            if(rectF.bottom < height){
                offsetY = height - rectF.bottom;
            }
        }

        //图片的宽或高小于屏幕的宽或搞，平移至屏幕中心
        if(rectF.width() < width){
            offsetX = 0.5f * width - rectF.right + 0.5f * rectF.width();
        }
        if(rectF.height() < height){
            offsetY = 0.5f * height - rectF.bottom + 0.5f * rectF.height();
        }
        //平移
        mMatrix.postTranslate(offsetX, offsetY);
    }
}
