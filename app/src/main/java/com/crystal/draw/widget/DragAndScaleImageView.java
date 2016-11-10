package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 自定义实现图片的拖拽、缩放操作.
 * Created by xpchi on 2016/11/10 0010.
 */

public class DragAndScaleImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener{

    private static final String TAG = DragAndScaleImageView.class.getSimpleName();

    private static final int DRAG_MODE = 1; //拖拽模式(translate)
    private static final int SCALE_MODE = 2; //缩放模式(scale)

    private PointF startPointF = new PointF(); //拖拽时开始坐标
    private PointF middlePoint = new PointF(); //缩放时的中间点坐标

    private Matrix mCurrentMatrix; //记录当前矩阵
    private Matrix mMatrix;

    private float startDistance; //两个手指的开始的距离
    private float endDistance; //两个手指的结束的距离

    private boolean mFirst = true; //因为视图树有可能会改变多次，所以加上此标记防止程序多次执行。
    private int mode; //状态模式

    private float initScale = 1.0f; //初始化缩放比率
    private float maxScale = 8.0f; //最大缩放scale

    //存放矩阵数据
    private float[] matrixValues = new float[9];

    private boolean isMax = false;
    private boolean isMin = false;



    public DragAndScaleImageView(Context context) {
        this(context, null);
    }

    public DragAndScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //view附加上窗体上
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //添加视图树的监听
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    //view从窗体上移除
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除视图树的监听
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 监听视图树的变化获取view的宽高并显示图片位置
     */
    @Override
    public void onGlobalLayout() {
        if(mFirst){ //加此标记防止onGlobalLayout()因视图树的频繁改变而重复调用。
            Drawable drawable = getDrawable();
            if(drawable == null){
                return;
            }
            //获取view宽高
            int width = getWidth();
            int height = getHeight();
            //获取图片的宽高
            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();
            float scale = 1.0f; //默认不缩放
            //根据不同情况计算缩放scale
            if(dWidth > width && dHeight <= height){
                scale = width * 1.0f / dWidth;
            }
            if(dHeight > height && dWidth <= width){
                scale = height * 1.0f / dHeight;
            }
            if(dWidth > width && dHeight > height){
                //取最小缩放比率
                scale = Math.min(width * 1.0f / dWidth, height * 1.0f / dHeight);
            }
            //图片宽高都小于屏幕宽高，以屏幕宽度为主来缩放。最终图片宽度与屏幕一样宽。
            if(dWidth < width && dHeight < height){
                scale = width * 1.0f / dWidth;
            }
            initScale = scale; //保存默认的最小缩放比率
            mCurrentMatrix.set(getImageMatrix());
            mMatrix.set(mCurrentMatrix);
            //先平移至屏幕中心点，再缩放。
            mMatrix.postTranslate((width - dWidth) / 2, (height - dHeight) / 2);
            mMatrix.postScale(scale, scale, width / 2, height / 2);
            Log.d(TAG, "onGlobalLayout' mMatrix is " + mMatrix);
            setImageMatrix(mMatrix);
            mFirst = false;
        }
    }

    /**
     * 触摸监听
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //event.getAction()只支持单点触控，加上MotionEvent.ACTION_MASK就可以支持多点触控。
        int width = getWidth() / 2;
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN...");
                mode = DRAG_MODE; //设置拖拽模式
                mCurrentMatrix.set(getImageMatrix()); //获取当前矩阵
                startPointF.set(event.getX(), event.getY()); //记录每次单点触控按压的坐标
                break;
            case MotionEvent.ACTION_MOVE:
                /*if(mode == DRAG_MODE){
                    float dx = event.getX() - startPointF.x;
                    float dy = event.getY() - startPointF.y;
                    Log.d(TAG, "mMatrix is " +  mMatrix.toShortString());
                    mMatrix.set(mCurrentMatrix); //以当前矩阵为基础
                    mMatrix.postTranslate(dx, dy); //在当前矩阵的基础上进行平移
                    //disallowOverScreenRange(); //检测不允许超出屏幕范围
                }*/
                if(mode == DRAG_MODE/* && isMax*/){ //最大缩放状态时拖动
                    float dx = event.getX() - startPointF.x;
                    float dy = event.getY() - startPointF.y;
                    mMatrix.set(mCurrentMatrix); //以当前矩阵为基础
                    mMatrix.postTranslate(dx, dy); //在当前矩阵的基础上进行平移
                    disallowLeaveScreenBound(); //图片拖拽时不能离开屏幕的边界
                }
                if(mode == SCALE_MODE){
                    endDistance = calDistance(event); //缩放触点(手指)合拢或扩张
                    float scale = endDistance / startDistance;
                    Log.d(TAG, "scale, initScale is " + scale + " , " + initScale);
                    float preScale = getPreScale(); //获取上一次scale值

                    if(isMin){
                        /*//到最小缩放时，做放大操作，不阻止放大图片
                        if(endDistance > startDistance){
                            isMin = false;
                            return true;
                        }
                        //到最小缩放时，接着做缩小操作，阻止缩小图片
                        if(endDistance < startDistance){
                            return false;
                        }*/
                        if(scale * preScale <= initScale){ //最小缩放状态，阻止再次缩放。
                            return false;
                        }else{ //最小缩放状态，不阻止放大。
                            isMin = false;
                            return true;
                        }
                    }

                    if(isMax){
                        /*//到最大缩放时，做缩小操作，不阻止缩小图片。
                        if(endDistance < startDistance){
                            isMax = false;
                            return true;
                        }
                        //到最大缩放时，接着做放大操作，阻止放大图片。
                        if(endDistance > startDistance){
                            return false;
                        }*/
                        if(scale * preScale >= maxScale){ //最大缩放状态，阻止再次放大。
                            return false;
                        }else{ //最大缩放状态，不阻止缩小。
                            isMax = false;
                            return true;
                        }
                    }

                    if(preScale <= maxScale || preScale >= initScale){
                        //控制最小缩放边界
                        if(scale * preScale <= initScale){
                            scale = initScale / preScale;
                            isMin = true;
                        }
                        //控制最大缩放边界
                        if(scale * preScale >= maxScale){
                            scale = maxScale / preScale;
                            isMax = true;
                        }
                        mMatrix.set(mCurrentMatrix); //以当前矩阵为基础
                        mMatrix.postScale(scale, scale, middlePoint.x, middlePoint.y);
                        controllPicRangeInScreen(); //控制图片在屏幕中的范围
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_POINTER_UP: //当触点离开屏幕，但是屏幕上还有触点(手指)
                mode = 0; //取消模式
                break;
            case MotionEvent.ACTION_POINTER_DOWN: //当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                mode = SCALE_MODE; //设置缩放模式
                Log.d(TAG, "event.getPointerCount() is " + event.getPointerCount());
                startDistance = calDistance(event);
                middlePoint = calMiddlePointF(event);
                //获取当前矩阵
                mCurrentMatrix.set(getImageMatrix());
                break;
        }
        setImageMatrix(mMatrix); //保存当前矩阵并执行相关的矩阵变换
        return true;
    }

    /**
     * 拖拉时图片不得超出屏幕范围
     */
    private void disallowOverScreenRange(){
        RectF rectF = getMatrixRectF(); //根据图片当前的matrix获取图片的rectF范围
        float overX = 0.0f; //记录超出横坐标值
        float overY = 0.0f; //记录超出的纵坐标值
        //当前view的宽高(由于设置match_parent的缘故，所以其宽高分别与屏幕宽高是一致的)
        int width = getWidth();
        int height = getHeight();
        if(rectF.left < 0){ //超出左边屏幕
            overX = -rectF.left;
        }
        if(rectF.right > width){ //超出右边屏幕
            overX = width - rectF.right;
        }
        if(rectF.top < 0){ //超出顶部屏幕
            overY = -rectF.top;
        }
        if(rectF.bottom > height){ //超出底部屏幕
            overY = height - rectF.bottom;
        }
        mMatrix.postTranslate(overX, overY); //平移到屏幕范围内
    }

    /**
     * 控制图片缩放后在屏幕中的范围
     */
    private void controllPicRangeInScreen(){
        RectF rectF = getMatrixRectF();
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

    /**
     * 图片拖拽时不能离开屏幕的边界
     */
    private void disallowLeaveScreenBound(){
        RectF rectF = getMatrixRectF();
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
     * 根据当前图片的matrix获得图片的范围
     */
    private RectF getMatrixRectF(){
        Matrix matrix = mMatrix;
        Drawable drawable = getDrawable();
        RectF rectF = new RectF();
        if(null != drawable){
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 计算两个触点(手指)间的距离
     * @param event
     */
    private float calDistance(MotionEvent event){
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        //根据勾股定律计算出距离
        return (float) Math.sqrt(dx * dx +  dy * dy);
    }

    /**
     * 获取两个触点(手指)间的中间点坐标
     * 屏幕范围内x、y轴都为正方向
     * @param event
     * @return
     */
    private PointF calMiddlePointF(MotionEvent event){
        PointF pointF = new PointF();
        pointF.x = (event.getX(0) + event.getX(1)) / 2;
        pointF.y = (event.getY(0) + event.getY(1)) / 2;
        return pointF;
    }

    /**
     * 获取矩阵scale值
     * @return
     */
    private float getPreScale(){
        mMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }
    /**
     * 初始化数据
     */
    private void init(){
        mCurrentMatrix = new Matrix();
        mMatrix = new Matrix();
    }
}
