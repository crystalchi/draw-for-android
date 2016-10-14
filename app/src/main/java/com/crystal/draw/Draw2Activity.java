package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.crystal.draw.widget.MyView1;
import com.crystal.draw.widget.MyView2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * http://blog.csdn.net/harvic880925/article/details/38926877
 * Created by Administrator on 2016/10/12 0012.
 */

public class Draw2Activity extends Activity {

    @BindView(R.id.root)
    FrameLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw2);
        ButterKnife.bind(this);
        MyView2 myView2 = new MyView2(this);
        root.addView(myView2);
    }
}
