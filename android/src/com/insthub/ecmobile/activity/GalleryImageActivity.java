
package com.insthub.ecmobile.activity;
//

//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.external.HorizontalVariableListView.widget.HorizontalListView;
import com.external.activeandroid.util.Log;
import com.external.easing.Back;
import com.external.easing.Cubic;
import com.external.easing.Expo;
import com.external.easing.Sine;
import com.insthub.BeeFramework.Utils.Utils;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.GalleryImageAdapter;
import com.umeng.analytics.MobclickAgent;

public class GalleryImageActivity extends BaseActivity
		implements OnGestureListener, OnTouchListener
{

	private ViewPager imagePager;

	private GalleryImageAdapter galleryImageAdapter;

	private SharedPreferences shared;

	private SharedPreferences.Editor editor;

	private int pager_num;

	int total_page;

	FrameLayout backgroundLayout;

	HorizontalScrollView background_srcollview;

	HorizontalScrollView layer_srcollview;

	int backgoundWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_image);

		shared = getSharedPreferences("userInfo", 0);
		editor = shared.edit();

		boolean isFirstRun = shared.getBoolean("isFirstRun", true);
		if (!isFirstRun)
		{
			jumpActivity();
		}

		initLayout();
		backgroundLayout = (FrameLayout) findViewById(R.id.backgroundLayout);
		background_srcollview = (HorizontalScrollView) findViewById(
				R.id.background_srcollview);
		background_srcollview.setHorizontalScrollBarEnabled(false);

		layer_srcollview = (HorizontalScrollView) findViewById(
				R.id.layer_srcollview);
		layer_srcollview.setHorizontalScrollBarEnabled(false);

		imagePager = (ViewPager) findViewById(R.id.image_pager);

		galleryImageAdapter = new GalleryImageAdapter(this);
		imagePager.setAdapter(galleryImageAdapter);
		imagePager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				pager_num = position + 1;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels)
			{
				float realOffset = Cubic.easeIn(positionOffset, 0, 1, 1);

				total_page = galleryImageAdapter.getCount();
				float offset = (float) ((float) (position + realOffset) * 1.0
						/ total_page);
				int offsetPositon = (int) (backgoundWidth * offset);

				float layerRealOffset = Sine.easeIn(positionOffset, 0, 1, 1);
				float layerOffset = (float) ((float) (position
						+ layerRealOffset) * 1.0 / total_page);
				int layerOffsetPositon = (int) (backgoundWidth * layerOffset);
				layer_srcollview.scrollTo(layerOffsetPositon, 0);
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{
			}
		});

		imagePager.setOnTouchListener(this);

	}

	GestureDetector mygesture = new GestureDetector(this);

	public boolean onTouch(View v, MotionEvent event)
	{
		return mygesture.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY)
	{
		if (e1.getX() - e2.getX() > 120)
		{
			if (pager_num == 5)
			{
				jumpActivity();
				editor.putBoolean("isFirstRun", false);
				editor.commit();
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY)
	{
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		return false;
	}

	void initLayout()
	{
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		backgoundWidth = dm.widthPixels * 5;
		// ViewGroup.LayoutParams layoutParams;

		ImageView back_image_one = (ImageView) findViewById(
				R.id.back_image_one);
		ImageView back_image_two = (ImageView) findViewById(
				R.id.back_image_two);
		ImageView back_image_three = (ImageView) findViewById(
				R.id.back_image_three);
		ImageView back_image_four = (ImageView) findViewById(
				R.id.back_image_four);
		ImageView back_image_five = (ImageView) findViewById(
				R.id.back_image_five);

		initBackImage(dm, back_image_one);
		initBackImage(dm, back_image_two);
		initBackImage(dm, back_image_three);
		initBackImage(dm, back_image_four);
		initBackImage(dm, back_image_five);

		// FrameLayout.LayoutParams frameLayoutParams;
		ImageView layer_image_one = (ImageView) findViewById(
				R.id.layer_image_one);
		ImageView layer_image_two = (ImageView) findViewById(
				R.id.layer_image_two);
		ImageView layer_image_three = (ImageView) findViewById(
				R.id.layer_image_three);
		ImageView layer_image_four = (ImageView) findViewById(
				R.id.layer_image_four);
		ImageView layer_image_five = (ImageView) findViewById(
				R.id.layer_image_five);

		initLayerImage(dm, layer_image_one);
		initLayerImage(dm, layer_image_two);
		initLayerImage(dm, layer_image_three);
		initLayerImage(dm, layer_image_four);
		initLayerImage(dm, layer_image_five);
	}

	private void initLayerImage(DisplayMetrics dm, ImageView layer_image_one)
	{
		FrameLayout.LayoutParams frameLayoutParams;
		frameLayoutParams = (FrameLayout.LayoutParams) layer_image_one
				.getLayoutParams();
		frameLayoutParams.height = dm.heightPixels;
		frameLayoutParams.width = dm.widthPixels;
		layer_image_one.setLayoutParams(frameLayoutParams);
	}

	private void initBackImage(DisplayMetrics dm, ImageView back_image_one)
	{
		ViewGroup.LayoutParams layoutParams;
		layoutParams = back_image_one.getLayoutParams();
		layoutParams.height = dm.heightPixels;
		layoutParams.width = dm.widthPixels;
		back_image_one.setLayoutParams(layoutParams);
	}

	private void jumpActivity()
	{
		Intent intent = new Intent(this, EcmobileMainActivity.class);
		startActivity(intent);
		finish();
	}
}
