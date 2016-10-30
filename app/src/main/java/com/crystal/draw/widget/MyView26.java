package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xpchi on 2016/10/30.
 */

public class MyView26 extends View{


    private Paint mPaint;

    public MyView26(Context context) {
        super(context);
        init();
    }

    public MyView26(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView26(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*canvas.drawColor(Color.RED);

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(100, 0, 200, 100, mPaint);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100, 0, 200, 100, mPaint);*/





        /*canvas.drawColor(Color.RED);

        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint,
                Canvas.MATRIX_SAVE_FLAG*//*|Canvas.HAS_ALPHA_LAYER_SAVE_FLAG*//*);
        canvas.rotate(40);
        canvas.drawRect(100, 0, 200, 100, mPaint);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100, 0, 200, 100, mPaint);*/






        /*canvas.drawColor(Color.RED);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint,
                Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(100, 200, 100, 300, mPaint);
        canvas.restore();*/

        /*canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.clipRect(100, 100, 200, 200);
        canvas.drawColor(Color.GREEN);
        canvas.restore();
        canvas.drawColor(Color.YELLOW);*/





        /*canvas.drawColor(Color.YELLOW);
        //canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint,
                Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.rotate(30);
        canvas.drawRect(200, 100, 300, 200, mPaint);
        canvas.restore();
        mPaint.setColor(Color.RED);
        canvas.drawRect(200, 100, 300, 200, mPaint);*/


        /*canvas.drawColor(Color.RED);
        //canvas.save(Canvas.MATRIX_SAVE_FLAG);
        //canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.clipRect(100, 0, 200, 100);
        canvas.restore();
        //canvas.drawColor(Color.YELLOW);*/




        canvas.drawColor(Color.RED);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint,
                Canvas.MATRIX_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        //canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(30);
        canvas.drawRect(200, 100, 300, 200, mPaint);
        canvas.restore();
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(200, 100, 300, 200, mPaint);

    }

    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
    }
}
