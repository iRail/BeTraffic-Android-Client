package com.profete162.WebcamWallonnes;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItem;
import android.util.Log;

import com.profete162.WebcamWallonnes.misc.Snippets;
import com.profete162.WebcamWallonnes.radar.Radar;

public class RadarActivity extends FragmentActivity {
	/** Called when the activity is first created. */

	static FragmentActivity fa;

	static double GPS[];

	ArrayList<Radar> stationList = new ArrayList<Radar>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_radar);
		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Loading...");
		RadarFragment fragment = (RadarFragment) getSupportFragmentManager()
				.findFragmentById(R.id.radarFragment);
		fragment.GPS = Snippets.getLocationFromBundle(this.getIntent().getExtras());

	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			finish();

		default:
			Log.i("", "ID: " + item.getItemId());

		}

		return super.onOptionsItemSelected(item);

	}

}