package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.crystal.draw.widget.MyView6;
import com.crystal.draw.widget.MyView7;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * http://blog.csdn.net/harvic880925/article/details/50423762
 * Created by Administrator on 2016/10/12 0012.
 */

public class Draw7Activity extends Activity {

    @BindView(R.id.myview7)
    MyView7 myView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_clear)
    void clear(){
        myView7.clear();
    }
}
