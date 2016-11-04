package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.crystal.draw.R;

/**
 * paint.setXfermode之SRC_IN模式加深理解
 * Created by Administrator on 2016/11/3 0003.
 */

public class MyView32 extends View{

    private Paint mPaint;
    private Context mContext;
    private Bitmap mDstBitmap; //目标图
    private Bitmap mSrcBitmap; //源图
    private int w = 600;
    private int h = 600;

    public MyView32(Context context) {
        super(context);
        init(context);
    }

    public MyView32(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView32(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建全新透明的bitmap画布图层
        int layerId = canvas.saveLayer(0, 0, w, h, mPaint, Canvas.ALL_SAVE_FLAG);
        //画目标图
        canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);
        //设置DST_IT模式的混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //画源图
        canvas.drawBitmap(mSrcBitmap, 0, 0, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);
        //还原上一层画布
        canvas.restoreToCount(layerId);
    }


    /**
     * 初始化
     * @param context
     */
    private void init(Context context){
        mContext = context;
        mPaint = new Paint();

        //创建Dst目标图
        mSrcBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); //创建画布
        Canvas c = new Canvas(mSrcBitmap); //创建画板
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(context.getResources().getColor(R.color.color_c37e00));
        c.drawRect(new Rect(0, 0, 600, 600), p);

        //创建Src源图
        mDstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.crystal);
    }

    /*
        总结：
        SRC_IN [Sa * Da, Sc * Da]
        从这个效果来看，根据这个SRC_IN模式的公式就能知道。
        这个结果值就是由源图的透明度Sa和颜色值Sc,分别乘以目标图的透明度Da来计算。
        当目标图的透明度Da为0(也就是为空白像素)时，计算的结果也会为空白像素。

        然后结合效果就得知，不管是SRC_IN模式还是SRC模式都是显示源图像，当目标图的透明度为0(空白像素)时，
        与目标图相交的源图部分就会变为空白。


        实际应用总结：
        在实际应用中，我们可以从下面三个方面来决定使用哪一个模式：
        1、首先，目标图像和源图像混合，需不需要生成颜色的叠加特效，如果需要叠加特效则从颜色叠加相关模式中选择，
        有Mode.ADD（饱和度相加）、Mode.DARKEN（变暗），Mode.LIGHTEN（变亮）、Mode.MULTIPLY（正片叠底）、Mode.OVERLAY（叠加），Mode.SCREEN（滤色）
        2、当不需要特效，而需要根据某一张图像的透明像素来裁剪时，就需要使用SRC相关模式或DST相关模式了。由于SRC相关模式与DST相关模式是相通的，
        唯一不同的是决定当前哪个是目标图像和源图像；
        3、当需要清空图像时，使用Mode.CLEAR
    */
}
