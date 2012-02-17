package com.profete162.WebcamWallonnes;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.profete162.WebcamWallonnes.Weather.Weather;
import com.profete162.WebcamWallonnes.misc.Snippets;

public class WeatherActivity extends FragmentActivity {
	Boolean tabsSet = false;
	ViewPager mViewPager;

	static ArrayList<Weather> list=new ArrayList<Weather>();
	static double GPS[];
	public static HashMap<Integer, Integer> codeLink = new HashMap<Integer, Integer>();
	static FragmentActivity fa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_weather);
		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Loading...");
		WeatherFragment fragment = (WeatherFragment) getSupportFragmentManager()
				.findFragmentById(R.id.weatherFragment);
		setCodeLink();
		fragment.GPS = Snippets.getLocationFromBundle(this.getIntent().getExtras());


	}
	
	public void setCodeLink() {
		codeLink.put(395, R.drawable.w12);
		codeLink.put(392, R.drawable.w16);
		codeLink.put(389, R.drawable.w24);
		codeLink.put(386, R.drawable.w16);
		codeLink.put(377, R.drawable.w21);
		codeLink.put(374, R.drawable.w13);
		codeLink.put(371, R.drawable.w12);
		codeLink.put(368, R.drawable.w11);
		codeLink.put(365, R.drawable.w13);
		codeLink.put(362, R.drawable.w13);
		codeLink.put(359, R.drawable.w18);
		codeLink.put(356, R.drawable.w10);
		codeLink.put(353, R.drawable.w09);
		codeLink.put(350, R.drawable.w21);
		codeLink.put(338, R.drawable.w20);
		codeLink.put(335, R.drawable.w12);
		codeLink.put(332, R.drawable.w20);
		codeLink.put(329, R.drawable.w20);
		codeLink.put(326, R.drawable.w11);
		codeLink.put(323, R.drawable.w11);
		codeLink.put(320, R.drawable.w19);
		codeLink.put(317, R.drawable.w21);
		codeLink.put(314, R.drawable.w21);
		codeLink.put(311, R.drawable.w21);
		codeLink.put(308, R.drawable.w18);
		codeLink.put(305, R.drawable.w10);
		codeLink.put(302, R.drawable.w18);
		codeLink.put(299, R.drawable.w10);
		codeLink.put(296, R.drawable.w17);
		codeLink.put(293, R.drawable.w17);
		codeLink.put(284, R.drawable.w21);
		codeLink.put(281, R.drawable.w21);
		codeLink.put(266, R.drawable.w17);
		codeLink.put(263, R.drawable.w09);
		codeLink.put(260, R.drawable.w07);
		codeLink.put(248, R.drawable.w07);
		codeLink.put(230, R.drawable.w20);
		codeLink.put(227, R.drawable.w19);
		codeLink.put(200, R.drawable.w16);
		codeLink.put(185, R.drawable.w21);
		codeLink.put(182, R.drawable.w21);
		codeLink.put(179, R.drawable.w13);
		codeLink.put(176, R.drawable.w09);
		codeLink.put(143, R.drawable.w06);
		codeLink.put(122, R.drawable.w04);
		codeLink.put(119, R.drawable.w03);
		codeLink.put(116, R.drawable.w02);
		codeLink.put(113, R.drawable.w01);

	}

	
}
