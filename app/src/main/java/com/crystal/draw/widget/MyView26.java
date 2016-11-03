package com.crystal.draw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CancellationSignal;
import android.util.AttributeSet;
import android.view.View;

/**
 * Layer图层二之save系列函数的FLAG具体意义
 * 参考：
 * https://github.com/GcsSloop/AndroidNote/issues/7
 * http://blog.csdn.net/harvic880925/article/details/51332494
 * save与saveLayer的区别：
 * save不会生成全新的bitmap，saveLayer或saveLayerAlpha会生成全新的bitmap。
 * 并且这个全新的bitmap是透明的。
 *
 * 由于这个案例中会使用到一些硬件加速不支持的函数，所以需要禁止硬件加速。
 * 不支持的函数列表请参考：http://blog.csdn.net/harvic880925/article/details/51264653
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

        //使用save/saveLayer函数的FLAG之Canvas.MATRIX_SAVE_FLAG

        /*1.canvas.save(Canvas.MATRIX_SAVE_FLAG);保存的是canvas的位置矩阵信息。
        所以进行画布的位置变化后是可以将变化的画布恢复到原来的状态。*/

        /*//设置矩阵位置信息，画布做旋转操作，可恢复画布原来的状态。
        canvas.drawColor(Color.RED);

        canvas.save(Canvas.MATRIX_SAVE_FLAG); //设置位置矩阵标记，保存矩阵位置信息。
        canvas.rotate(20); //画布旋转
        canvas.drawRect(100, 0, 200, 100, mPaint);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100, 0, 200, 100, mPaint);*/


        //设置矩阵位置信息，画布做裁剪操作，不可恢复画布原来的状态。
        /*canvas.drawColor(Color.RED);

        canvas.save(Canvas.MATRIX_SAVE_FLAG); //设置位置矩阵标记，保存矩阵位置信息。
        canvas.clipRect(new Rect(100, 0, 200, 100)); //裁剪
        canvas.drawColor(Color.GREEN);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);*/



        /*3.使用saveLayer函数结合Canvas.HAS_ALPHA_LAYER_SAVE_FLAG标记，画布做旋转操作产生的效果,位置恢复并且是原来的状态。*/
        /*canvas.drawColor(Color.RED);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(new Rect(100, 0, 200, 100), mPaint);
        canvas.restoreToCount(layerId);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(new Rect(100, 0, 200, 100), mPaint);*/


        /*4.设置了Canvas.MATRIX_SAVE_FLAG标记，但是画布做了裁剪操作，位置恢复了，大小未恢复。*/
        /*canvas.drawColor(Color.RED);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.clipRect(100, 0, 200, 100);
        canvas.drawColor(Color.YELLOW);
        canvas.restoreToCount(layerId);

        canvas.drawColor(Color.GREEN);*/

        /*总结：

          由以上1、2两个测试程序表明，使用save函数时，canvas.save(Canvas.MATRIX_SAVE_FLAG)只保存画布位置信息。
          当使用canvas.rotate(20);画布旋转操作后，调用restore()可以恢复原来的位置，并且画板颜色也是全屏

          但是在这种设置下，当裁剪画布，调用restore()之后，只恢复了画布的位置信息，但是画布大小未恢复。
          所以再次调用canvas.drawColor(Color.YELLOW);时，所画的大小没有还原成原来的全屏画布状态，
          还是我们裁剪的大小的画布。

        由3、4两个测试程序表明使用saveLayer函数，创建一个全新透明的bitmap(即画布)。当恢复画布后，
        在全新透明画布上所绘制的内容会覆盖到上一层画布上，最后呈现一个重合的图像内容。
        当使用saveLayer函数时，除了设置Canvas.MATRIX_SAVE_FLAG位置矩阵标记之外，还设置了一个
        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG标记，这个标记的意义是在恢复原来的画布时，全新透明的bitmap所在区域
        的原来画布(上一层)的内容不清空，这里的原来画布(上一层)绘制的内容不会被清除掉。
        如果去掉这个Canvas.HAS_ALPHA_LAYER_SAVE_FLAG标记，那上一层的内容全部会被清空。
        所以如果有时在新建的全新透明bitmap上所作绘制内容后，需要恢复原来画布时，如果需要保留原来画布(上一层)的内容与新建bitmap上
        所绘制的内容做一个重合(覆盖在上一层上的)效果，就需要加上Canvas.HAS_ALPHA_LAYER_SAVE_FLAG)标记。
        不需要保留，就不需要加上这个Canvas.HAS_ALPHA_LAYER_SAVE_FLAG标记。

        结论：
        使用save/saveLayer函数，当设置保存状态的标记为Canvas.MATRIX_SAVE_FLAG时，restore/restoreToCount只能恢复位置信息状态，大小无法恢复。
        使用Canvas.HAS_ALPHA_LAYER_SAVE_FLAG标记，restore/restoreToCount时，全新透明的bitmap(使用saveLayer函数创建的)所在区域的
        上一层(调用saveLayer新建全新bitmap之前的一层)所绘制的内容不会被清除。
        */






        //使用save/saveLayer函数FLAG之CLIP_SAVE_FLAG


        /*1. canvas.drawColor(Color.RED);

        int layerId = canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(100, 0, 200, 100);
        canvas.restore();

        canvas.drawColor(Color.GREEN);*/


        /*2. canvas.drawColor(Color.RED);
        canvas.drawRect(new Rect(100, 0, 200, 100), mPaint);
        int layerId = canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.rotate(20);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(new Rect(100, 0, 200, 100), mPaint);*/



        /*3. canvas.drawColor(Color.RED);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.clipRect(100, 0, 200, 100);
        canvas.restoreToCount(layerId);

        canvas.drawColor(Color.YELLOW);*/


        /*canvas.drawColor(Color.RED);
        canvas.drawRect(new Rect(100, 0, 200, 100), mPaint);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.rotate(40);
        canvas.restoreToCount(layerId);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(new Rect(100, 0, 200, 100), mPaint);*/


        /*总结结论：

        使用save/saveLayer函数，当设置保存状态的标记为Canvas.CLIP_SAVE_FLAG，restore/restoreToCount只能恢复大小(即裁剪)状态，位置无法恢复。
        使用Canvas.HAS_ALPHA_LAYER_SAVE_FLAG标记，restore/restoreToCount时，全新透明的bitmap(使用saveLayer函数创建的)所在区域的
        上一层(调用saveLayer新建全新bitmap之前的一层)所绘制的内容不会被清除。
        */








        //使用Canvas.FULL_COLOR_LAYER_SAVE_FLAG标记
        /*1. canvas.drawColor(Color.RED);

        int layerId = canvas.saveLayer(0, 0, 500, 500, null, Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
        canvas.drawRect(new Rect(100, 100, 300, 300), mPaint);
        canvas.restoreToCount(layerId);*/

        //使用Canvas.HAS_ALPHA_LAYER_SAVE_FLAG标记
        /*2. canvas.drawColor(Color.RED);

        int layerId = canvas.saveLayer(0, 0, 500, 500, null, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.drawRect(new Rect(100, 100, 300, 300), mPaint);
        canvas.restoreToCount(layerId);*/

        //同时设置Canvas.FULL_COLOR_LAYER_SAVE_FLAG标记与Canvas.HAS_ALPHA_LAYER_SAVE_FLAG标记
        /*3. canvas.drawColor(Color.RED);

        int layerId = canvas.saveLayer(0, 0, 500, 500, null, Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.drawRect(new Rect(100, 100, 300, 300), mPaint);
        canvas.restoreToCount(layerId);*/


        /*4. canvas.drawColor(Color.RED);

        int layerId = canvas.saveLayer(0, 0, 500, 500, null, Canvas.MATRIX_SAVE_FLAG);
        canvas.drawRect(new Rect(100, 100, 300, 300), mPaint);
        canvas.restoreToCount(layerId);*/


        /*
            总结结论：

            FLAG之HAS_ALPHA_LAYER_SAVE_FLAG和FULL_COLOR_LAYER_SAVE_FLAG
            这两个标识都是saveLayer()专用的
            HAS_ALPHA_LAYER_SAVE_FLAG表示新建的bitmap画布在与上一个画布合成时，不会将上一层画布内容清空，直接盖在上一个画布内容上面。
            FULL_COLOR_LAYER_SAVE_FLAG则表示新建的bimap画布在与上一个画布合成时，先将上一层画布对应区域清空，然后再盖在上面。
            注意一定要在view中禁用掉硬件加速，因为在api 21之后，才支持saveLayer

            HAS_ALPHA_LAYER_SAVE_FLAG和FULL_COLOR_LAYER_SAVE_FLAG两个标记同时设置时，以HAS_ALPHA_LAYER_SAVE_FLAG为主。

            从第4个案例的效果图可以看出，在默认情况下使用的是Canvas.FULL_COLOR_LAYER_SAVE_FLAG标识，即先清空上一层画布对应区域的图像，然后再合成，所以这也是我们在上面的例子中强制添加HAS_ALPHA_LAYER_SAVE_FLAG标识的原因
            所以有关这两个标识的结论来了：
            1、HAS_ALPHA_LAYER_SAVE_FLAG表示新建的bitmap画布在与上一个画布合成时，不会将上一层画布内容清空，直接盖在上一个画布内容上面。
            2、FULL_COLOR_LAYER_SAVE_FLAG则表示新建的bimap画布在与上一个画布合成时，先将上一层画布对应区域清空，然后再盖在上面。
            3、当HAS_ALPHA_LAYER_SAVE_FLAG与FULL_COLOR_LAYER_SAVE_FLAG两个标识同时指定时，以HAS_ALPHA_LAYER_SAVE_FLAG为主
            4、当即没有指定HAS_ALPHA_LAYER_SAVE_FLAG也没有指定FULL_COLOR_LAYER_SAVE_FLAG时，系统默认使用FULL_COLOR_LAYER_SAVE_FLAG；
        */




        //CLIP_TO_LAYER_SAVE_FLAG
        /*从效果图中可以看出，当我们调用canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG)时，
        canvas画板就被裁剪了，不仅影响了自己，而且还把view的原始画布给影响了，
        虽然在调用了canvas.restore()，但最后一句在将原始画布填充为黄色，也可以看出，原始画布没有被恢复！ */
        /*canvas.drawColor(Color.RED);
        canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);*/


        //Canvas.CLIP_SAVE_FLAG 和 Canvas.CLIP_TO_LAYER_SAVE_FLAG共用
        //从效果图中可以看出canvas被恢复了，不过canvas被恢复也，也就失去了Canvas.CLIP_TO_LAYER_SAVE_FLAG标识的意义了。
        canvas.drawColor(Color.RED);
        canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.CLIP_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);

        /*
            所以这个CLIP_TO_LAYER_SAVE_FLAG标识的结论来了：
            1、CLIP_TO_LAYER_SAVE_FLAG意义是在新建bitmap前，先把canvas给裁剪，一旦画板被裁剪，那么其中的各个画布都会被受到影响。
            而且由于它是在新建bitmap前做的裁剪，所以是无法恢复的；
            2、当CLIP_TO_LAYER_SAVE_FLAG与CLIP_SAVE_FLAG标识共用时，在调用restore()后，画布将被恢复
        */



        /*
            最后总结：

                6、FLAG之ALL_SAVE_FLAG
                这个标识是我们最常用的，它是所有标识的公共集合。
                对于save(int flag)来讲，ALL_SAVE_FLAG = MATRIX_SAVE_FLAG | CLIP_SAVE_FLAG；即保存位置信息和裁剪信息
                对于save(int flag)来讲，ALL_SAVE_FLAG = MATRIX_SAVE_FLAG | CLIP_SAVE_FLAG很容易理解，
                因为save(int flag)函数只能使用MATRIX_SAVE_FLAG 、CLIP_SAVE_FLAG这两个标识。
                对于saveLayer(int flag)来讲，ALL_SAVE_FLAG = MATRIX_SAVE_FLAG | CLIP_SAVE_FLAG|HAS_ALPHA_LAYER_SAVE_FLAG；
                即保存保存位置信息和裁剪信息，新建画布在与上一层画布合成时，不清空原画布内容。
                原本来讲saveLayer的ALL_SAVE_FLAG标识应当是它所能使用的所有标识的集合，
                即应当是ALL_SAVE_FLAG = MATRIX_SAVE_FLAG | CLIP_SAVE_FLAG|HAS_ALPHA_LAYER_SAVE_FLAG|FULL_COLOR_LAYER_SAVE_FLAG|CLIP_TO_LAYER_SAVE_FLAG,
                但由于HAS_ALPHA_LAYER_SAVE_FLAG与FULL_COLOR_LAYER_SAVE_FLAG共用时以HAS_ALPHA_LAYER_SAVE_FLAG为主，CLIP_TO_LAYER_SAVE_FLAG与CLIP_SAVE_FLAG共用时，
                CLIP_TO_LAYER_SAVE_FLAG将无效，所以最终ALL_SAVE_FLAG = MATRIX_SAVE_FLAG | CLIP_SAVE_FLAG|HAS_ALPHA_LAYER_SAVE_FLAG；

        */



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




        /*canvas.drawColor(Color.RED);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint,
                Canvas.MATRIX_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        //canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(30);
        canvas.drawRect(200, 100, 300, 200, mPaint);
        canvas.restore();
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(200, 100, 300, 200, mPaint);*/

    }

    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null); //禁用硬件加速，防止占用内存。（由于会用到不支持硬件加速的函数，所以直接禁用掉硬件加速功能。）
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
    }
}
