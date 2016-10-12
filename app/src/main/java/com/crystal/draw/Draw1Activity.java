package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.crystal.draw.widget.MyView1;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/12 0012.
 */

public class Draw1Activity extends Activity {

    @BindView(R.id.root)
    FrameLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw1);
        ButterKnife.bind(this);
        MyView1 myView1 = new MyView1(this);
        root.addView(myView1);
    }
}
