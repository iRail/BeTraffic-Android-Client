package com.profete162.WebcamWallonnes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentMapActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.gson.Gson;
import com.profete162.WebcamWallonnes.TrafficActivity.Traffic;
import com.profete162.WebcamWallonnes.TrafficActivity.TrafficList;
import com.profete162.WebcamWallonnes.adapter.DataBaseHelper;
import com.profete162.WebcamWallonnes.misc.ItemizedOverlayPerso;
import com.profete162.WebcamWallonnes.misc.Snippets;
import com.profete162.WebcamWallonnes.radar.Radar;

public class MyMapActivity extends FragmentMapActivity {

	CustomizedLocationOverlay myLocationOverlay;
	MapView mMap;
	DataBaseHelper mDbHelper;
	private ProgressDialog m_ProgressDialog;
	Activity context;
	RadarOverlay radarOverlay;
	TrafficOverlay trafficOverlay;
	List<Radar> radarList;
	TrafficList obj = null;
	Cursor camCursor;
	int zoom = 13;
	GeoPoint myLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		mMap = (MapView) findViewById(R.id.myGmap);
		mMap.setBuiltInZoomControls(true);
		mMap.setSatellite(false);

		context = this;

		mDbHelper = new DataBaseHelper(this);
		try {
			mDbHelper.createDataBase();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		CameraOverlay cameraOverlay = new CameraOverlay();

		mDbHelper.openDataBase(DataBaseHelper.DB_NAME_WEBCAM);
		camCursor = mDbHelper.fetchAllWebcam();
		for (camCursor.moveToFirst(); camCursor.moveToNext(); camCursor
				.isAfterLast()) {
			float lat = Float.valueOf(camCursor.getString(camCursor
					.getColumnIndex("Lat")));
			float lon = Float.valueOf(camCursor.getString(camCursor
					.getColumnIndex("Lon")));
			Log.i("", "CAM" + lat + " - " + lon);
			GeoPoint gp = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			cameraOverlay.addPoint(gp);
		}
		mDbHelper.close();
		mMap.getOverlays().add(cameraOverlay);
		float lat = (float) 50.13;
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
			if (mMap.getZoomLevel() >= zoom) {
				Dialog dialog = new Dialog(context);
				dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.speed_dialog);
				TextView tvSpeed = (TextView) dialog
						.findViewById(R.id.tv_speedlimit);
				tvSpeed.setText("" + radarList.get(index).getSpeedLimit());
				dialog.show();
				radarList.get(index).getSpeedLimit();
			} else
				Toast.makeText(context, getString(R.string.zoom),
						Toast.LENGTH_LONG).show();
			return true;
		}
	}

	public class TrafficOverlay extends ItemizedOverlayPerso {

		public TrafficOverlay() {
			super(getResources().getDrawable(R.drawable.ic_work_small));
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean onTap(int index) {
			if (mMap.getZoomLevel() >= zoom) {

				Traffic clickedItem = obj.getTraffics().getItems().get(index);

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage(clickedItem.getDesc())
						.setTitle(clickedItem.getLocation())
						.setCancelable(false)
						.setNeutralButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								});

				builder.create().show();
			} else
				Toast.makeText(context, getString(R.string.zoom),
						Toast.LENGTH_LONG).show();
			return true;
		}
	}

	public class CameraOverlay extends ItemizedOverlayPerso {
		ImageView image;

		public CameraOverlay() {
			super(getResources().getDrawable(R.drawable.ic_cam_small));
			// TODO Auto-generated constructor stub
		}

		public class DownloadPicTask extends AsyncTask<String, Integer, Bitmap> {
			protected Bitmap doInBackground(String... url) {
				Bitmap bm = null;
				try {
					final URLConnection conn = new URL(url[0]).openConnection();
					conn.connect();
					final BufferedInputStream bis = new BufferedInputStream(
							conn.getInputStream());
					bm = BitmapFactory.decodeStream(bis);
					bis.close();

				} catch (IOException e) {
					Log.d("DEBUGTAG", "Oh noooz an error...");
				}
				return bm;
			}

			protected void onPostExecute(Bitmap result) {
				image.setImageBitmap(result);
			}

		}

		@Override
		protected boolean onTap(int index) {
			if (mMap.getZoomLevel() >= zoom) {
				Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.custom_dialog);
				camCursor.moveToPosition(index+1);
				int picId = Integer.valueOf(camCursor.getString(camCursor
						.getColumnIndex("_id")));
				char cat = camCursor.getString(camCursor.getColumnIndex("Cat"))
						.charAt(0);

				dialog.setTitle(camCursor.getString(camCursor
						.getColumnIndex("City")));

				image = (ImageView) dialog.findViewById(R.id.image);
				image.setImageResource(R.drawable.icon);

				new DownloadPicTask().execute(Snippets
						.getUrlFromCat(picId, cat));

				dialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
				dialog.getWindow().getAttributes().height = LayoutParams.FILL_PARENT;
				dialog.show();
			} else
				Toast.makeText(context, getString(R.string.zoom),
						Toast.LENGTH_LONG).show();
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
		public void onLocationChanged(final Location loc) {
			super.onLocationChanged(loc);
			myLocation = new GeoPoint((int) (loc.getLatitude() * 1E6),
					(int) (loc.getLongitude() * 1E6));
			;
			if (!isFirst) {
				m_ProgressDialog = new ProgressDialog(context);
				m_ProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				m_ProgressDialog.setCancelable(false);
				m_ProgressDialog
						.setMessage("Getting 50 closest Radars and Traffic Events");
				m_ProgressDialog.show();
				isFirst = true;
				radarOverlay = new RadarOverlay();
				Runnable get50ClosestRadarsRunnable = new Runnable() {
					public void run() {

						mDbHelper.openDataBase(DataBaseHelper.DB_NAME_RADAR);
						radarList = mDbHelper.fetchAllRadarCloseTo(loc);

						// i est inutile, est il possible de prendre facilement
						// les 50
						// premiers éléments d'une liste???
						int i = 0;
						for (Radar aRadar : radarList) {
							m_ProgressDialog.setProgress(i);

							double lat = aRadar.getLat();
							double lon = aRadar.getLon();
							GeoPoint gp = new GeoPoint((int) (lat * 1E6),
									(int) (lon * 1E6));
							radarOverlay.addPoint(gp);
							i++;
							if (i > 50)
								break;
						}

						mDbHelper.close();
						context.runOnUiThread(setTrafficMessage);
						Runnable get50ClosestTrafficEventsRunnable = new Runnable() {
							public void run() {

								String url = "http://data.beroads.com/IWay/TrafficEvent/"
										+ getString(R.string.lan)
										+ "/all.json?max=50&from="
										+ loc.getLatitude()
										+ ","
										+ loc.getLongitude() + "&area=100";
								System.out.println("*** URL:" + url);

								try {

									// Log.i("MY INFO",
									// "Json Parser started..");
									Gson gson = new Gson();
									Reader r = new InputStreamReader(
											TrafficFragment.getJSONData(url));
									// Log.i("MY INFO", r.toString());
									obj = gson.fromJson(r, TrafficList.class);

								} catch (Exception ex) {
									ex.printStackTrace();

								}
								trafficOverlay = new TrafficOverlay();
								int i = 0;
								try {
									for (Traffic aTraffic : obj.getTraffics()
											.getItems()) {
										m_ProgressDialog.setProgress(i);

										double lat = aTraffic.getLat();
										double lon = aTraffic.getLon();
										GeoPoint gp = new GeoPoint(
												(int) (lat * 1E6),
												(int) (lon * 1E6));
										trafficOverlay.addPoint(gp);
										i++;
										if (i > 50)
											break;
									}
									context.runOnUiThread(addAllOverlays);
								} catch (Exception e) {
									e.printStackTrace();
									context.runOnUiThread(addOverlays);
								}

							}
						};
						// context.runOnUiThread(get50ClosestRadarsRunnable);
						Thread thread = new Thread(null,
								get50ClosestTrafficEventsRunnable,
								"get50ClosestTrafficEventsRunnable");
						thread.start();
					}
				};
				// context.runOnUiThread(get50ClosestRadarsRunnable);
				Thread thread = new Thread(null, get50ClosestRadarsRunnable,
						"get50ClosestRadarsRunnable");
				thread.start();
			}
		}

	}

	private Runnable addAllOverlays = new Runnable() {
		public void run() {

			mMap.getOverlays().add(radarOverlay);
			mMap.getOverlays().add(myLocationOverlay);
			mMap.getOverlays().add(trafficOverlay);
			mMap.getController().setZoom(zoom);
			mMap.getController().setCenter(myLocation);
			// Mais pourquoi l'overlay met il tant de temps à s'afficher????
			// Le mettre à la suite dans un thread ne semble pas aider...
			context.runOnUiThread(hideProgessDialog);

		}
	};
	private Runnable addOverlays = new Runnable() {
		public void run() {

			mMap.getOverlays().add(radarOverlay);
			mMap.getOverlays().add(myLocationOverlay);
			mMap.getController().setZoom(zoom);
			mMap.getController().setCenter(myLocation);
			// Mais pourquoi l'overlay met il tant de temps à s'afficher????
			// Le mettre à la suite dans un thread ne semble pas aider...
			context.runOnUiThread(hideProgessDialog);

		}
	};

	private Runnable hideProgessDialog = new Runnable() {
		public void run() {
			m_ProgressDialog.dismiss();
		}
	};

	private Runnable setTrafficMessage = new Runnable() {
		public void run() {
			m_ProgressDialog.setTitle("Getting 50 closest Traffic Events");
		}
	};

}
