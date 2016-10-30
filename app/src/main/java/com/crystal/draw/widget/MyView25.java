package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Layer图层
 * Created by xpchi on 2016/10/29.
 */

public class MyView25 extends View {

    private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG |
            Canvas.CLIP_SAVE_FLAG |
            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
            Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
            Canvas.CLIP_TO_LAYER_SAVE_FLAG;

    private Paint mPaint;

    public MyView25(Context context) {
        super(context);
        init();
    }

    public MyView25(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView25(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(150, 150, 100, mPaint);

        /*canvas.saveLayerAlpha(0, 0, 400, 400, 127, LAYER_FLAGS);*/
        /*canvas.saveLayerAlpha(0, 0, 400, 400, 0, LAYER_FLAGS);*/
        /*canvas.saveLayerAlpha(0, 0, 400, 400, 255, LAYER_FLAGS);*/

        canvas.saveLayer(0, 0, 400, 400, mPaint, Canvas.ALL_SAVE_FLAG);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(200, 200, 100, mPaint);
        canvas.restore();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
