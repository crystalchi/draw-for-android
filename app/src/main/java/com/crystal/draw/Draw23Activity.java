package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.crystal.draw.widget.MyView23;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class Draw23Activity extends Activity {

    @BindView(R.id.myView)
    MyView23 myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw23);
        ButterKnife.bind(this);
        myView.startAnimation();
    }
}
