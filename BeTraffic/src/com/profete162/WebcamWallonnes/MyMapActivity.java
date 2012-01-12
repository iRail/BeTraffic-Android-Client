package com.profete162.WebcamWallonnes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentMapActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.profete162.WebcamWallonnes.adapter.DataBaseHelper;
import com.profete162.WebcamWallonnes.misc.ItemizedOverlayPerso;
import com.profete162.WebcamWallonnes.radar.Radar;

public class MyMapActivity extends FragmentMapActivity {

	CustomizedLocationOverlay myLocationOverlay;
	MapView mMap;
	DataBaseHelper mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		mMap = (MapView) findViewById(R.id.myGmap);
		mMap.setBuiltInZoomControls(true);
		mMap.setSatellite(false);

		mDbHelper = new DataBaseHelper(this);
		try {
			mDbHelper.createDataBase();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		CameraOverlay cameraOverlay = new CameraOverlay();

		mDbHelper.openDataBase(DataBaseHelper.DB_NAME_WEBCAM);
		Cursor cursor = mDbHelper.fetchAllWebcam();
		for (cursor.moveToFirst(); cursor.moveToNext(); cursor.isAfterLast()) {
			float lat = Float.valueOf(cursor.getString(cursor
					.getColumnIndex("Lat")));
			float lon = Float.valueOf(cursor.getString(cursor
					.getColumnIndex("Lon")));
			Log.i("", "CAM" + lat + " - " + lon);
			GeoPoint gp = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			cameraOverlay.addPoint(gp);
		}
		mDbHelper.close();
		mMap.getOverlays().add(cameraOverlay);
		float lat = (float) 50.133;
		float lon = (float) 4.733;
		GeoPoint gp = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));

		mMap.getController().setCenter(gp);
		mMap.getController().setZoom(8);

		// ADD the blue dot with my position

		myLocationOverlay = new CustomizedLocationOverlay(this, mMap);
		
	}

	public class RadarOverlay extends ItemizedOverlayPerso {

		public RadarOverlay() {
			super(getResources().getDrawable(R.drawable.ic_radar_small));
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean onTap(int index) {
			return true;
		}
	}

	public class CameraOverlay extends ItemizedOverlayPerso {

		public CameraOverlay() {
			super(getResources().getDrawable(R.drawable.ic_cam_small));
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean onTap(int index) {
			return true;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(Menu.NONE, 0, Menu.NONE, "Traffic")
				.setIcon(R.drawable.ic_work)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(Menu.NONE, 1, Menu.NONE, "radar").setIcon(R.drawable.ic_radar)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(Menu.NONE, 2, Menu.NONE, "Camera").setIcon(R.drawable.ic_cam)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			break;

		case 1:
			break;

		case 2:
			break;

		case android.R.id.home:
			finish();

		default:
			Log.i("", "ID: " + item.getItemId());
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
	}

	@Override
	public void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
	}

	public class CustomizedLocationOverlay extends MyLocationOverlay {

		Boolean isFirst = false;

		public CustomizedLocationOverlay(Context context, MapView mapView) {
			super(context, mapView);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void onLocationChanged(Location loc) {
			super.onLocationChanged(loc);
			if (!isFirst) {
				isFirst = true;
				RadarOverlay radarOverlay = new RadarOverlay();
				mDbHelper.openDataBase(DataBaseHelper.DB_NAME_RADAR);
				List<Radar> radarList = mDbHelper.fetchAllRadarCloseTo(loc);
				// i est inutile, est il possible de prendre facilement les 50
				// premiers éléments d'une liste???
				int i = 0;
				for (Radar aRadar : radarList) {
					double lat = aRadar.getLat();
					double lon = aRadar.getLon();
					
					GeoPoint gp = new GeoPoint((int) (lat * 1E6),
							(int) (lon * 1E6));
					radarOverlay.addPoint(gp);
					i++;
					if(i>50)
						break;
				}
				
				mDbHelper.close();
				
				mMap.getOverlays().add(radarOverlay);
				mMap.getOverlays().add(myLocationOverlay);
				
				
				
			}
		}

	}

}
