package com.crystal.draw;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
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
    //Animation setAnimation;

    //addAnimation方法是AnimationSet类中自定义的。
    // 其父类Animation中没有此方法
    // 所以此处不能使用父类类型的引用指向子类对象
    //只能指定引用类型为子类的类型
    AnimationSet animationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim1);
        ButterKnife.bind(this);

        //alphaAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.alphaanim);
        alphaAnimation = new AlphaAnimation(1.0f, 0.1f);

        //scaleAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.scaleanim);
        scaleAnimation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //rotateAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.rotateanim);
        rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //translateAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.translateanim);
        translateAnimation = new TranslateAnimation(0, 100, 0, 100);

        //setAnimation = AnimationUtils.loadAnimation(Anim1Activity.this, R.anim.setanim);

        animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setFillBefore(true);
        animationSet.setDuration(3000);
    }

    @OnClick(R.id.btn_alpha_anim)
    void startAlphaAnim(){
        alphaAnimation.setDuration(3000);
        mTv.startAnimation(alphaAnimation);
    }

    @OnClick(R.id.btn_scale_anim)
    void startScaleAnim(){
        scaleAnimation.setDuration(3000);
        mTv.startAnimation(scaleAnimation);
    }

    @OnClick(R.id.btn_rotate_anim)
    void startRotateAnim(){
        rotateAnimation.setDuration(3000);
        mTv.startAnimation(rotateAnimation);
    }

    @OnClick(R.id.btn_translate_anim)
    void startTranslateAnim(){
        translateAnimation.setDuration(3000);
        mTv.startAnimation(translateAnimation);
    }

    @OnClick(R.id.btn_set_anim)
    void startSetAnimation(){
        mTv.startAnimation(animationSet);
    }

}
