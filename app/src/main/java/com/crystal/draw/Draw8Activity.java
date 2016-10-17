package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;

import com.crystal.draw.widget.MyView7;
import com.crystal.draw.widget.MyView8;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * http://blog.csdn.net/harvic880925/article/details/50423762
 * Created by Administrator on 2016/10/12 0012.
 */

public class Draw8Activity extends Activity {

    @BindView(R.id.myview8)
    MyView8 myView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw3);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_clear)
    void clear(){

    }
}
