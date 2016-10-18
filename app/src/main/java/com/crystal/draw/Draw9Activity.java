package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.crystal.draw.widget.MyView8;
import com.crystal.draw.widget.MyView9;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * http://blog.csdn.net/harvic880925/article/details/50423762
 * Created by Administrator on 2016/10/12 0012.
 */

public class Draw9Activity extends Activity {

    @BindView(R.id.root)
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw3);
        ButterKnife.bind(this);
        MyView9 myView9 = new MyView9(this);
        root.addView(myView9);
        myView9.startAnim();
    }

}
