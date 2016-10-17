package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.crystal.draw.widget.MyView2;
import com.crystal.draw.widget.MyView3;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * http://blog.csdn.net/harvic880925/article/details/39056701
 * Created by Administrator on 2016/10/12 0012.
 */

public class Draw3Activity extends Activity {

    @BindView(R.id.root)
    FrameLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw1);
        ButterKnife.bind(this);
        MyView3 myView3 = new MyView3(this);
        root.addView(myView3);
    }
}
