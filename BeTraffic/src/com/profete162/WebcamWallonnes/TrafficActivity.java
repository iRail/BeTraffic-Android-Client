package com.profete162.WebcamWallonnes;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItem;
import android.util.Log;

import com.profete162.WebcamWallonnes.misc.Snippets;

public class TrafficActivity extends FragmentActivity {
	Boolean tabsSet = false;
	FragmentActivity fa;
	public static HashMap<Integer, Integer> codeLink = new HashMap<Integer, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic);
		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Loading...");
		TrafficFragment fragment = (TrafficFragment) getSupportFragmentManager()
				.findFragmentById(R.id.trafficFragment);
		fragment.GPS = Snippets.getLocationFromBundle(this.getIntent().getExtras());

	}

	

	public class TrafficList {

		private TrafficEvent TrafficEvent;

		public TrafficEvent getTraffics() {
			return TrafficEvent;
		}
	}
	
	public class TrafficEvent{
		private List<Traffic> item;
		public List<Traffic> getItems() {
			return item;
		}
	}

	public class Traffic {
		private String source;
		private String name;
		private String message;
		private String location;
		private double lat;
		private double lng;
		int distance;

		public String getLocation() {
			return this.location;
		}
		
		public int getDistance() {
			return this.distance;
		}
		
		public String getSource() {
			return this.source;
		}

		public String getName() {
			return this.name;
		}

		public double getLat() {
			return this.lat;
		}

		public double getLon() {
			return this.lng;
		}

		public String getDesc() {
			return this.message;
		}

	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("index", getSupportActionBar()
				.getSelectedNavigationIndex());
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
