package com.profete162.WebcamWallonnes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.profete162.WebcamWallonnes.adapter.DataBaseHelper;
import com.profete162.WebcamWallonnes.adapter.RadarLocationAdapter;
import com.profete162.WebcamWallonnes.misc.Snippets;
import com.profete162.WebcamWallonnes.radar.Radar;

public class RadarFragment extends ListFragment {
	private Thread thread = null;
	private DataBaseHelper myDbHelper;
	ArrayList<Radar> radarList = new ArrayList<Radar>();
	ListFragment lf;
	public double GPS[];
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateListToLocation();

		myDbHelper = new DataBaseHelper(getActivity());
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		lf = this;
		try {
			myDbHelper.openDataBase(DataBaseHelper.DB_NAME_PARKING);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		myDbHelper.close();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		RadarLocationAdapter adapter = (RadarLocationAdapter) l.getAdapter();
		try {
			Radar clickedItem = (Radar) adapter.getItem(position);
			// TODO
			Intent streetIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("google.streetview:cbll=" + clickedItem.getLat()
							+ "," + clickedItem.getLon()));
			startActivity(streetIntent);

		} catch (Exception e) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.svTitle)
					.setMessage(R.string.svMessage)
					.setCancelable(false)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent goToMarket = new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("market://details?id=com.google.android.street"));
									try {
										startActivity(goToMarket);
									} catch (Exception e) {
										Toast.makeText(getActivity(),
												R.string.noMarket,
												Toast.LENGTH_LONG).show();

									}
								}
							})
					.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();

		}

	}

	private void updateListToLocation() {
		Runnable updateListRunnable = new Runnable() {
			public void run() {
				updateListToLocationThread(GPS[0], GPS[1]);
			}
		};
		//Log.v(TAG, "updateListToLocation");
		thread = new Thread(null, updateListRunnable, "MagentoBackground");
		thread.start();

	}

	/**
	 * The thread that is launched to read the database and compare each Station
	 * location to my current location.
	 */

	private void updateListToLocationThread(double lat, double lon) {

		myDbHelper.openDataBase(DataBaseHelper.DB_NAME_RADAR);
		// Cursor locationCursor = myDbHelper.fetchAllRadar();
		List<Radar> radarList = myDbHelper.fetchAllRadarCloseTo(lat, lon);

		//Log.i(TAG, "size in updateListToLocationThread: " + radarList.size());

		Looper.prepare();
		if (getActivity() != null) {
			final RadarLocationAdapter myRadarAdapter = new RadarLocationAdapter(
					getActivity(), R.layout.row_closest, radarList);
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					lf.setListAdapter(myRadarAdapter);
					getSupportActivity().getSupportActionBar().setTitle(
							getString(R.string.app_name));
				}
			});
		}

		myDbHelper.close();

	}

	public void compareStationsListToMyLocation(Cursor locationCursor, int i,
			double lat, double lon) {
		locationCursor.moveToPosition(i);
		String strName = locationCursor.getString(locationCursor
				.getColumnIndex("name"));

		double iLat = locationCursor.getDouble(locationCursor
				.getColumnIndex("lat"));

		double iLon = locationCursor.getDouble(locationCursor
				.getColumnIndex("lon"));

		int speedLimit = locationCursor.getInt(locationCursor
				.getColumnIndex("speedLimit"));

		double dDis = Snippets.getDistance(lat, lon, iLat, iLon);

		radarList.add(new Radar(strName, iLat, iLon, dDis + "", speedLimit, 0,
				locationCursor.getInt(locationCursor.getColumnIndex("id"))));
	}
}
