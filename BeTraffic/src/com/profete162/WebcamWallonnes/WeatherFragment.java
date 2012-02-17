package com.profete162.WebcamWallonnes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;

import com.profete162.WebcamWallonnes.Weather.Weather;
import com.profete162.WebcamWallonnes.adapter.WeatherAdapter;

public class WeatherFragment extends ListFragment {
	Boolean tabsSet = false;
	ViewPager mViewPager;

	static ArrayList<Weather> list = new ArrayList<Weather>();
	static double GPS[];

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getSupportActivity().getSupportActionBar().setTitle(parseWeather());
		this.setListAdapter(new WeatherAdapter(getActivity(),
				R.layout.row_weather, list, getActivity().getLayoutInflater()));
	}

	public void setWeatherTabs() {
		new SetWeatherTabs().execute();
	}

	
	public class SetWeatherTabs extends AsyncTask<String, Void, Void> {

		private String parsed;

		protected void onPreExecute() {
		}

		protected Void doInBackground(String... urls) {
			parsed = parseWeather();
			return null;
		}

		protected void onPostExecute(Void unused) {
			getSupportActivity().getSupportActionBar().setTitle(parsed);

			// ActionBar.Tab tab0 =
			// getSupportActionBar().newTab().setText(parsed);

			// mViewPager = (ViewPager) findViewById(R.id.pager);

			// mTabsAdapter = new TabsAdapter(fa, getSupportActionBar(),
			// mViewPager);

			// mTabsAdapter.addTab(tab0,
			// WeatherActivity.WeeklyWeatherFragment.class, 0);

		}

	}

	public static String parseWeather() {
		JSONObject dataObject = null;
		try {
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				ThreadPolicy tp = ThreadPolicy.LAX;
				StrictMode.setThreadPolicy(tp);

			}
			URL url;
			url = new URL(
					"http://free.worldweatheronline.com/feed/weather.ashx?q="
							+ GPS[0]
							+ ","
							+ GPS[1]
							+ "&key=1df836d286132805111508&num_of_days=5&includeLocation=yes&format=json");
			System.out.println("URL = " + url);
			URLConnection tc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					tc.getInputStream()));

			String line;
			// System.out.println("*********** ICI********");

			while ((line = in.readLine()) != null) {
				JSONObject jo = new JSONObject(line);
				dataObject = jo.getJSONObject("data");

				JSONArray weatherArray = dataObject.getJSONArray("weather");

				list.clear();

				for (int i = 0; i < weatherArray.length(); i++) {
					JSONObject weatherObject = (JSONObject) weatherArray.get(i);
					list.add(new Weather(weatherObject
							.getJSONArray("weatherDesc").getJSONObject(0)
							.getString("value"), weatherObject
							.getString("tempMaxC"), weatherObject
							.getString("tempMinC"), weatherObject
							.getString("winddir16Point"), weatherObject
							.getString("windspeedKmph"), weatherObject
							.getInt("weatherCode")));

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		try {
			return dataObject.getJSONArray("nearest_area").getJSONObject(0)
					.getJSONArray("areaName").getJSONObject(0)
					.getString("value");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

}
