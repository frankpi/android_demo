package com.example.tweenanim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.iv);
	}

	/**
	 * 透明度
	 * 
	 * @param view
	 */
	public void click01(View view) {
		AlphaAnimation animation = new AlphaAnimation(1.0f, 0.2f);
		animation.setDuration(2000);
		animation.setRepeatCount(3);
		animation.setRepeatMode(Animation.REVERSE);
		iv.startAnimation(animation);
	}

	/**
	 * 比例缩放
	 * 
	 * @param view
	 */
	public void click02(View view) {
		ScaleAnimation animation = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(2000);
		animation.setRepeatCount(3);
		animation.setRepeatMode(Animation.REVERSE);
		iv.startAnimation(animation);
	}

	/**
	 * xuanzhuan
	 * 
	 * @param view
	 */
	public void click03(View view) {
		RotateAnimation animation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(2000);
		animation.setRepeatCount(3);
		animation.setRepeatMode(Animation.REVERSE);
		iv.startAnimation(animation);
	}

	public void click04(View view) {
		TranslateAnimation animation = new TranslateAnimation(0, 20, 0, 0);
		animation.setDuration(2000);
		animation.setRepeatCount(3);
		animation.setRepeatMode(Animation.REVERSE);
		iv.startAnimation(animation);
	}

	public void click05(View view) {
		AnimationSet animationSet = new AnimationSet(this, null);

		ScaleAnimation sa = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		sa.setDuration(2000);
		sa.setRepeatCount(3);
		sa.setRepeatMode(Animation.REVERSE);

		RotateAnimation ra = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setDuration(2000);
		ra.setRepeatCount(3);
		ra.setRepeatMode(Animation.REVERSE);

		animationSet.addAnimation(ra);
		animationSet.addAnimation(sa);

		iv.startAnimation(animationSet);
	}

}
