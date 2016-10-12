package com.crystal.draw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.crystal.draw.R;
import com.crystal.draw.utils.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_draw_1)
    void draw1(){
        UIHelper.start(this, Draw1Activity.class);
    }

    @OnClick(R.id.btn_draw_2)
    void draw2(){
        UIHelper.start(this, Draw2Activity.class);
    }
}
