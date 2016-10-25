package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.crystal.draw.widget.MyView10;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * http://blog.csdn.net/harvic880925/article/details/50423762
 * Created by Administrator on 2016/10/12 0012.
 */

public class Draw10Activity extends Activity {

    @BindView(R.id.root)
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw3);
        ButterKnife.bind(this);
        MyView10 myView10 = new MyView10(this);
        root.addView(myView10);
//        myView10.startAnimator();
        //myView10.changeAnimator();
       // myView10.bombAnimator();
        //myView10.drawBomb();
    }

}
