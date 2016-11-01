package com.crystal.draw.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.crystal.draw.R;
import android.graphics.Paint;

import java.lang.ref.WeakReference;

/**
 * Android Xfermode实现圆角图片、圆形图片
 * Created by Administrator on 2016/10/31 0031.
 */

public class RoundOrCircleImageViewByXfermode extends ImageView{

    //圆角、圆形图片类型
    private int type;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;

    private Paint mPaint;
    private Paint mShapePaint;

    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mDstBitmap;

    private WeakReference<Bitmap> mWeakBitmap;

    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;

    private int mBorderColor = Color.parseColor("#f2f2f2");

    private int mBorderWidth = 10;

    private Bitmap mSrcBitMap;

    public RoundOrCircleImageViewByXfermode(Context context) {
        this(context, null);
    }

    public RoundOrCircleImageViewByXfermode(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 测量大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //如果是圆形，强制要求view的宽高一致，取宽高中的最小值
        if(type == TYPE_CIRCLE){
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);
        }
    }

   /* @Override
    public void invalidate()
    {
        mWeakBitmap = null;
        if (mMaskBitmap != null)
        {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        //在缓存中取出bitmap
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();

        if (null == bitmap || bitmap.isRecycled())
        {
            //拿到Drawable
            Drawable drawable = getDrawable();
            //获取drawable的宽和高
            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();

            if (drawable != null)
            {
                //创建bitmap
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                        Bitmap.Config.ARGB_8888);
                float scale = 1.0f;
                //创建画布
                Canvas drawCanvas = new Canvas(bitmap);
                //按照bitmap的宽高，以及view的宽高，计算缩放比例；因为设置的src宽高比例可能和imageview的宽高比例不同，这里我们不希望图片失真；
                if (type == TYPE_ROUND)
                {
                    // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
                    scale = Math.max(getWidth() * 1.0f / dWidth, getHeight()
                            * 1.0f / dHeight);
                } else
                {
                    scale = getWidth() * 1.0F / Math.min(dWidth, dHeight);
                }
                //根据缩放比例，设置bounds，相当于缩放图片了
                drawable.setBounds(0, 0, (int) (scale * dWidth),
                        (int) (scale * dHeight));
                drawable.draw(drawCanvas);
                if (mMaskBitmap == null || mMaskBitmap.isRecycled())
                {
                    mMaskBitmap = getBitmap();
                }
                // Draw Bitmap.
                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);
                //绘制形状
                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
                mPaint.setXfermode(null);
                //将准备好的bitmap绘制出来
                canvas.drawBitmap(bitmap, 0, 0, null);
                //bitmap缓存起来，避免每次调用onDraw，分配内存
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);
            }
        }
        //如果bitmap还存在，则直接绘制即可
        if (bitmap != null)
        {
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
            return;
        }

    }
    */

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Drawable drawable = getDrawable(); //获取该view在xml文件中设置的drawable对象
        if(drawable == null){
            return;
        }
        Bitmap dstBitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
        int dWidth = drawable.getIntrinsicWidth();
        int dHeight = drawable.getIntrinsicHeight();
        float scale = getScale(drawable, dWidth, dHeight); //获得缩放比率
        if(null == mSrcBitMap || mSrcBitMap.isRecycled()){
            mSrcBitMap = drawShape(drawable); //获得绘制形状bitmap（源图）
        }
        if(null == dstBitmap || dstBitmap.isRecycled()){
            dstBitmap = drawableToBitamp(drawable, scale, dWidth, dHeight); //获得缩放后的目标图bitmap
            setXfermodeHandle(drawable, dstBitmap, canvas); //图像混合
            //使用弱引用缓存bitmap，防止内存泄露。不管任何时候都可以让JVM回收。
            //bitmap缓存起来，避免每次调用onDraw，分配内存
            mWeakBitmap = new WeakReference<Bitmap>(dstBitmap);
        }
        //如果bitmap还存在，则直接绘制即可
        if(dstBitmap != null){
            dstBitmap = setScaledBitmap(drawable, dstBitmap, scale, dWidth, dHeight);
            setXfermodeHandle(drawable, dstBitmap, canvas);
        }
    }

    /**
     * 每次调用invalidate时，将缓存清除.
     */
    @Override
    public void invalidate(){
        mWeakBitmap = null;
        if (mSrcBitMap != null){
            mSrcBitMap.recycle();
            mSrcBitMap = null;
        }
        super.invalidate();
    }


    /**
     * 设置图像混合操作
     * @param drawable
     * @param dstBitmap
     * @param canvas
     */
    private void setXfermodeHandle(Drawable drawable, Bitmap dstBitmap, Canvas canvas){
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG); //创建一张全新透明的画布
        //画目标图
        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
        //设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画源图
        canvas.drawBitmap(mSrcBitMap, 0, 0, mPaint);
        mPaint.setXfermode(null); //还原混合模式
        canvas.restoreToCount(layerId); //出栈，将这张全新透明画布上画的内容全部覆盖贴在上一张画布上
    }

    /**
     * 绘制形状
     * @return
     *//*
    public Bitmap getBitmap()
    {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        if (type == TYPE_ROUND)
        {
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                    mBorderRadius, mBorderRadius, paint);
        } else
        {
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2,
                    paint);
        }

        return bitmap;
    }
*/


    private void init(Context context, AttributeSet attrs){
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mShapePaint = new Paint();
        mShapePaint.setAntiAlias(true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundOrCircleImageViewByXfermode);
        mBorderRadius = ta.getDimensionPixelSize(
                R.styleable.RoundOrCircleImageViewByXfermode_borderRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                BODER_RADIUS_DEFAULT, getResources()
                                        .getDisplayMetrics()));// 默认为10dp
        type = ta.getInt(R.styleable.RoundOrCircleImageViewByXfermode_type, TYPE_CIRCLE); //未指定类型时，默认图片是圆形
        ta.recycle(); //释放
    }


    /**
     * 实际图片的宽高与view的宽高不一致，所以要计算他们之间的缩放比率。
     * drawable转bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable, float scale, int dWidth, int dHeight) {
        //创建一张空白的画布
        Bitmap bitmap = Bitmap.createBitmap((int) (scale * dWidth), (int)(scale * dHeight), Bitmap.Config.ARGB_8888);
        //缩放图片
        bitmap = setScaledBitmap(drawable, bitmap, scale, dWidth, dHeight);
        return bitmap; //返回bitmap
    }

    /**
     * 根据缩放比率设置drawable的大小
     * @param drawable
     * @param bitmap
     * @param scale
     * @param dWidth
     * @param dHeight
     */
    private Bitmap setScaledBitmap(Drawable drawable, Bitmap bitmap, float scale, int dWidth, int dHeight){
        Canvas c = new Canvas(bitmap); //放入画板上
        //设置drawable的大小
        drawable.setBounds(0, 0,
                (int) (scale * dWidth), (int)(scale * dHeight));
        drawable.draw(c); //将drawable画在bitmap上，drawable在这里只能通过bitmap显示
        return bitmap;
    }

    /**
     * 计算缩放比率
     * @param drawable
     * @return
     */
    private float getScale(Drawable drawable, int dWidth, int dHeight){
        float scale = 1.0f;
        if(type == TYPE_CIRCLE){
            //取最大的缩放比，才能保证宽高缩放大小能与view的宽高一致。
            // 所以实际宽高需要从宽高中取一个最小的值，最后得到的缩放比才是最大的。
            scale = getWidth() * 1.0f / Math.min(dWidth, dHeight);
        }else{
            //取最大的缩放比率
            scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);
        }
        return scale;
    }

    /**
     * 绘制形状
     * @param drawable
     * @return
     */
    private Bitmap drawShape(Drawable drawable){
        //创建一张空白的画布
        Bitmap srcBitMap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(srcBitMap); //创建一张画板
        if(type == TYPE_CIRCLE){
            //在这张空白画布上srcBitMap画内容
            c.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mShapePaint);
        }else{
            //在这张空白画布上srcBitMap画内容
            c.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), getHeight() / 2, getHeight() / 2, mShapePaint);
        }
        return srcBitMap;
    }

    /**
     * 绘制最外面的边框
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {

        final Paint mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        if(type == TYPE_CIRCLE){
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - mBorderWidth / 2  , mBorderPaint);
        }else{
           /* canvas.drawRoundRect(new RectF(0, 0, getWidth() - mBorderWidth / 3 * 2, getHeight() - mBorderWidth / 3 * 2),
                    getHeight() / 2, getHeight() / 2, mBorderPaint);*/

        }
    }

}
