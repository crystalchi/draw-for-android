package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * http://blog.csdn.net/harvic880925/article/details/39996643
 * Created by Administrator on 2016/11/24 0024.
 */

public class Anim1Activity extends Activity{

    @BindView(R.id.tv)
    TextView mTv;

    Animation alphaAnimation;
    Animation scaleAnimation;
    Animation rotateAnimation;
    Animation translateAnimation;
    Animation setAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim1);
        ButterKnife.bind(this);
        alphaAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.alphaanim);
        scaleAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.scaleanim);
        rotateAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.rotateanim);
        translateAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.translateanim);
        setAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.setanim);
    }

    @OnClick(R.id.btn_alpha_anim)
    void startAlphaAnim(){
        mTv.startAnimation(alphaAnimation);
    }

    @OnClick(R.id.btn_scale_anim)
    void startScaleAnim(){
        mTv.startAnimation(scaleAnimation);
    }

    @OnClick(R.id.btn_rotate_anim)
    void startRotateAnim(){
        mTv.startAnimation(rotateAnimation);
    }

    @OnClick(R.id.btn_translate_anim)
    void startTranslateAnim(){
        mTv.startAnimation(translateAnimation);
    }

    @OnClick(R.id.btn_set_anim)
    void startSetAnimation(){
        mTv.startAnimation(setAnimation);
    }

}
