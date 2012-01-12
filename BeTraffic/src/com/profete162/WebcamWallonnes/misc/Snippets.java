package com.profete162.WebcamWallonnes.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.profete162.WebcamWallonnes.SortMap;

public class Snippets {

	public static void createImageMapReceiver(int picId, char cat,
			ImageView image, SortMap context) {

		String url = getUrlFromCat(picId, cat);
		context.new ImageMapReceiver(url, context, image);

	}

	public static float getDistanceBetweenLocations(Location loc1, Location loc2) {
		float[] results = { 999999999 };
		Location.distanceBetween(loc1.getLatitude(), loc1.getLongitude(),
				loc2.getLatitude(), loc2.getLongitude(), results);
		return results[0];
	}

	public static double[] getLocationFromBundle(Bundle bundle) {
		double[] toReturn = { bundle.getDouble("lat"), bundle.getDouble("lng") };
		return toReturn;
	}

	public static double getDistance(double sLat, double sLon, double eLat,
			double eLon) {
		double d2r = (Math.PI / 180);
		Log.i("DISTANCE: ", sLat + "/" + sLon + " * " + eLat + "/" + eLon);
		try {
			double dlong = (eLon - sLon) * d2r;
			double dlat = (eLat - sLat) * d2r;
			double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(sLat * d2r)
					* Math.cos(eLat * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

			return 6367 * c * 1000;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getUrlFromCat(int picId, char cat) {
		switch (cat) {
		case 'A':
			return "http://webcams-wallonnes.be/webcams/image_antwerpen_"
					+ (picId - 101) + ".jpg";
		case 'B':
			return "http://webcams-wallonnes.be/webcams/image_brussel_"
					+ (picId - 201) + ".jpg";
		case 'C':
			return "http://webcams-wallonnes.be/webcams/image_ringbxl_"
					+ (picId - 301) + ".jpg";
		case 'D':
			return "http://webcams-wallonnes.be/webcams/image_gand_"
					+ (picId - 401) + ".jpg";
		case 'E':
			return "http://webcams-wallonnes.be/webcams/image_lummen_"
					+ (picId - 501) + ".jpg";
		case 'F':
			return "http://webcams-wallonnes.be/webcams/image" + picId + ".jpg";
		default:
			return null;
		}
	}

	public static InputStream retrieveStream(String url, Context context) {

		DefaultHttpClient client = new DefaultHttpClient();

		HttpGet request = new HttpGet(url);

		// TODO: stocker la version pour ne pas faire un appel à chaque fois.
		String myVersion = "0.0";
		PackageManager manager = context.getPackageManager();
		try {
			myVersion = (manager.getPackageInfo(context.getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		request.setHeader("User-Agent", "Waza_Be: BeTrains " + myVersion
				+ " for Android");

		Log.w("getClass().getSimpleName()", "URL TO CHECK " + url);

		try {
			HttpResponse response = client.execute(request);
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("getClass().getSimpleName()", "Error " + statusCode
						+ " for URL " + url);
				return null;
			}

			HttpEntity getResponseEntity = response.getEntity();
			Log.w("getClass().getSimpleName()", "Read the url:  " + url);
			return getResponseEntity.getContent();

		} catch (IOException e) {
			Log.w("getClass().getSimpleName()", " Error for URL " + url, e);
		}

		return null;
	}

	public class CopyInputStream {
		private InputStream _is;
		private ByteArrayOutputStream _copy = new ByteArrayOutputStream();

		/**
	    	 * 
	    	 */
		public CopyInputStream(InputStream is) {
			_is = is;

			try {
				copy();
			} catch (IOException ex) {
				// do nothing
			}
		}
		
		private int copy() throws IOException {
			int read = 0;
			int chunk = 0;
			byte[] data = new byte[256];

			while (-1 != (chunk = _is.read(data))) {
				read += data.length;
				_copy.write(data, 0, chunk);
			}

			return read;
		}

		public InputStream getCopy() {
			return (InputStream) new ByteArrayInputStream(_copy.toByteArray());
		}
	}

	public static InputStream DownloadJsonFromUrl(String url, Context context) {
		InputStream source = retrieveStream(url, context);
		

		return source;
	}
	
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
}
