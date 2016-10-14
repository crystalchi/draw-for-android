package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * http://blog.csdn.net/harvic880925/article/details/39080931
 * Created by Administrator on 2016/10/14 0014.
 */

public class MyView4 extends View {

    public MyView4(Context context) {
        super(context);
    }

    public MyView4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //操作不可逆
        /*canvas.drawColor(Color.RED);
        canvas.clipRect(new Rect(100, 100, 200, 200));
        canvas.drawColor(Color.GREEN);*/

        //操作可逆
        /*canvas.drawColor(Color.RED); //当前画布大小为整个满屏

        //当前画布大小为(100, 100, 200, 200)
        canvas.save(); //保存整个画布（满屏）
        canvas.clipRect(new Rect(100, 100, 200, 200));
        canvas.drawColor(Color.GREEN);

        //当前画布大小恢复为满屏
        canvas.restore(); //恢复满屏
        canvas.drawColor(Color.YELLOW);*/

        //save到栈中，每restore一次就是从当前的栈顶就是删除一个当前栈顶画布的状态。
        // 新的栈顶保存的就是最新的画布状态,可放开如下代码测试看看效果
        /*canvas.drawColor(Color.RED);
        //保存的画布大小为全屏幕大小
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        //保存画布大小为Rect(100, 100, 800, 800)
        canvas.save();

        canvas.clipRect(new Rect(200, 200, 700, 700));
        canvas.drawColor(Color.BLUE);
        //保存画布大小为Rect(200, 200, 700, 700)
        canvas.save();

        canvas.clipRect(new Rect(300, 300, 600, 600));
        canvas.drawColor(Color.BLACK);
        //保存画布大小为Rect(300, 300, 600, 600)
        canvas.save();

        canvas.clipRect(new Rect(400, 400, 500, 500));
        canvas.drawColor(Color.WHITE);

        //将栈顶的画布状态取出来，作为当前画布，并画成黄色背景
        canvas.restore();
        canvas.drawColor(Color.YELLOW);*/



        //调用三次restore，从栈中删除三个画布状态，栈底的画布状态就是最后呈现的状态
        canvas.drawColor(Color.RED);
        //保存的画布大小为全屏幕大小
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        //保存画布大小为Rect(100, 100, 800, 800)
        canvas.save();

        canvas.clipRect(new Rect(200, 200, 700, 700));
        canvas.drawColor(Color.BLUE);
        //保存画布大小为Rect(200, 200, 700, 700)
        canvas.save();

        canvas.clipRect(new Rect(300, 300, 600, 600));
        canvas.drawColor(Color.BLACK);
        //保存画布大小为Rect(300, 300, 600, 600)
        canvas.save();

        canvas.clipRect(new Rect(400, 400, 500, 500));
        canvas.drawColor(Color.WHITE);

        //连续出栈三次，将最后一次出栈的Canvas状态作为当前画布，并画成黄色背景
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.drawColor(Color.YELLOW);



    }
}
