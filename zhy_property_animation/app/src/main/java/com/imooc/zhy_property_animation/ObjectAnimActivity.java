package com.imooc.zhy_property_animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.immoc.zhy_property_animation.R;

public class ObjectAnimActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.object_animator);
	}

	public void rotateyAnimRun(final View view)
	{
//		ObjectAnimator anim = ObjectAnimator//
//				.ofFloat(view, "zhy", 1.0F,  0.2F)//
//				.setDuration(500);//
//		anim.start();
//
//		anim.addUpdateListener(new AnimatorUpdateListener()
//		{
//			@Override
//			public void onAnimationUpdate(ValueAnimator animation)
//			{
//				float cVal = (Float) animation.getAnimatedValue();
//				view.setAlpha(cVal);
//				view.setScaleX(cVal);
//				view.setScaleY(cVal);
//			}
//		});
	}

	// ObjectAnimator anim = ObjectAnimator//
	// .ofFloat(view, "alpha", 1.0f, 0.25f, 0.75f, 0.15f, 0.5f, 0.0f)//
	// .setDuration(5000);
	// anim.setInterpolator(new LinearInterpolator());
	// anim.start();
	// anim.setInterpolator(TimeInterpolator)

}
