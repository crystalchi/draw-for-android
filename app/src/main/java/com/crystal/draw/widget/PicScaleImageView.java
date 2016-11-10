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

    public static final float SCALE_MAX = 4.0f; //最大缩放比率
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    private boolean mFirst = true;

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
     *
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //前提：每一次手势缩放的效果都会与上一次矩阵的缩放比相关联。这点儿好好领悟。
        //比如 preMatrix * handleMatrix....
        float preScaleValue = getScaleValue(); //获取当前矩阵的缩放比率值
        float scaleFactor = detector.getScaleFactor(); //获取此时手势缩放比率
        //缩放变大、缩小
        //scaleFactor大于1.0f代表手势在扩大图片，小于1.0f代表手势在缩小图片
        if ((preScaleValue <= SCALE_MAX && scaleFactor > 1.0f)
                || (preScaleValue >= initScale && scaleFactor < 1.0f)) {
            //控制最大范围边界
            if (scaleFactor * preScaleValue > SCALE_MAX) {
                scaleFactor = SCALE_MAX / preScaleValue;
            }
            //控制最小范围边界
            if (scaleFactor * preScaleValue < initScale) {
                scaleFactor = initScale / preScaleValue;
            }
            //手势缩放图片
            mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            //平移至中心位置
            controllRange();
            setImageMatrix(mMatrix); //应用缩放
        }
        return true; //处理缩放手势事件
    }

    /**
     * 此处必须得返回true才能执行onScale缩放事件
     *
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
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    private float getScaleValue() {
        mMatrix.getValues(matrixValues); //将当前matrix矩阵的前9位值赋值给matrixValues数组
        return matrixValues[Matrix.MSCALE_X]; //这里Matrix.MSCALE_X与Matrix.MSCALE_Y两个下标的值都是一样的，所以随便指定一个下标就行
    }

    /**
     * 当view附加上窗体时调用此方法
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this); //添加监听视图树事件
    }

    /**
     * 当view从窗体移除时调用此方法
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this); //移除监听视图树的事件
    }


    /**
     * 此处作首次默认显示缩放的图片
     * 当onResume()回调完成后，可使用addOnGlobalLayoutListener监听视图树改变后可在此
     * 回调方法中获取view的宽高。
     */
    @Override
    public void onGlobalLayout() {
        if (mFirst) { //因为onGlobalLayout()会被多次调用，所以此处加上一个标识防止多次调用
            int width = getWidth(); //获取view的宽
            int height = getHeight(); //获取view的高
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            int drawableWidth = drawable.getIntrinsicWidth(); //获取图片的宽度
            int drawableHeight = drawable.getIntrinsicHeight(); //获取图片的高度

            //图片实际宽高与view宽高的大小四种情况比较,计算不同情况下的缩放比率。
            float scale = 1.0f; //默认1.0f不缩放，一样大(图片宽高分别等于view的宽高的情况)
            //1.图片宽大于view的宽, 图片的高不大于view高的情况：
            if (drawableWidth > width && drawableHeight <= height) {
                scale = width * 1.0f / drawableWidth;
            }
            //2.图片高大于view的高，图片的宽不大于view宽的情况：
            if (drawableHeight > height && drawableWidth <= width) {
                scale = height * 1.0f / drawableHeight;
            }
            //3.图片宽高都分别大于view的宽高的情况：
            if (drawableWidth > width && drawableHeight > height) {
                scale = Math.min(width * 1.0f / drawableWidth, height * 1.0f / drawableHeight);
            }
            //4.图片宽高都分别小于view的宽高的情况：
            if (drawableWidth < width && drawableHeight < height) {
                scale = Math.max(width * 1.0f / drawableWidth, height * 1.0f / drawableHeight);
            }
            initScale = scale; //记录第一次默认显示的scale值。
            //以view中心为中心平移缩放图片。
            //此处混合变换一定要注意调用顺序,否则平移的值会变化有误差。
            mMatrix.preScale(scale, scale, width / 2, height / 2);
            mMatrix.preTranslate((width - drawableWidth) / 2, (height - drawableHeight) / 2); //平移至view的中心
            setImageMatrix(mMatrix); //应用matrix
            mFirst = false; //标识为false，防止多次调用
        }
    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        Log.d(TAG, "rect.width() is " + rect.width());
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
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
    private RectF getMatrixRectF() {
        Matrix matrix = mMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            Log.d(TAG, "rect before is " + rect.toShortString());
            matrix.mapRect(rect);
        }
        Log.d(TAG, "rect after is " + rect.toShortString());
        return rect;
    }

    /**
     * 控制四边与view边界间距不能有空白
     */
    private void controllRange() {
        RectF rect = getMatrixRectF();
        int width = getWidth();
        int height = getHeight();
        float offsetX = 0.0f, offsetY = 0.0f;
        //缩放后的图片的宽大于view的宽
        if (rect.width() >= width) {
            if (rect.left > 0) {
                offsetX = -rect.left;
            }
            if (rect.right < width) {
                offsetX = width - rect.right;
            }
        }
        //缩放后的图片的高大于view的高
        if (rect.height() >= height) {
            if (rect.top > 0) {
                offsetY = -rect.top;
            }
            if (rect.bottom < height) {
                offsetY = height - rect.bottom;
            }
        }
        //缩放后的图片宽或高小于view的宽或者高，平移到屏幕中心(即view中心)
        if (rect.width() < width) {
            offsetX = width * 0.5f - rect.right + rect.width() * 0.5f;
        }
        if (rect.height() < height) {
            offsetY = height * 0.5f - rect.bottom + rect.height() * 0.5f;
        }
        mMatrix.postTranslate(offsetX, offsetY);
    }
}
