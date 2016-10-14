package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.crystal.draw.widget.MyView3;
import com.crystal.draw.widget.MyView4;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * http://blog.csdn.net/harvic880925/article/details/39080931
 * Created by Administrator on 2016/10/12 0012.
 */

public class Draw4Activity extends Activity {

    @BindView(R.id.root)
    FrameLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw2);
        ButterKnife.bind(this);
        MyView4 myView4 = new MyView4(this);
        root.addView(myView4);
    }
}
