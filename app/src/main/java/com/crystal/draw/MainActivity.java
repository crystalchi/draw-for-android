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

    @OnClick(R.id.btn_draw_3)
    void draw3(){
        UIHelper.start(this, Draw3Activity.class);
    }

    @OnClick(R.id.btn_draw_4)
    void draw4(){
        UIHelper.start(this, Draw4Activity.class);
    }

    @OnClick(R.id.btn_draw_5)
    void draw5(){
        UIHelper.start(this, Draw5Activity.class);
    }

    @OnClick(R.id.btn_draw_6)
    void draw6(){
        UIHelper.start(this, Draw6Activity.class);
    }

    @OnClick(R.id.btn_draw_7)
    void draw7(){
        UIHelper.start(this, Draw7Activity.class);
    }

    @OnClick(R.id.btn_draw_8)
    void draw8(){
        UIHelper.start(this, Draw8Activity.class);
    }

    @OnClick(R.id.btn_draw_9)
    void draw9(){
        UIHelper.start(this, Draw9Activity.class);
    }

    @OnClick(R.id.btn_draw_10)
    void draw10(){
        UIHelper.start(this, Draw10Activity.class);
    }

    @OnClick(R.id.btn_draw_11)
    void draw11(){
        UIHelper.start(this, Draw11Activity.class);
    }
}
