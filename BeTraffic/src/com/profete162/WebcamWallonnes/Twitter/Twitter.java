package com.profete162.WebcamWallonnes.Twitter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.profete162.WebcamWallonnes.R;
import com.profete162.WebcamWallonnes.misc.Snippets;

public class Twitter {

	
	public static void getTweets(final Activity a, final ListView l) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String url = "http://search.twitter.com/search.json?q=BETRAINS";
					SharedPreferences mDefaultPrefs = PreferenceManager
							.getDefaultSharedPreferences(a);
					;
					if (mDefaultPrefs.getBoolean("mHarkor",true));
						url += "%20OR%20harkor";
					if (mDefaultPrefs.getBoolean("mQkaiser",true))
						url += "%20OR%20QKaiser";
					if (mDefaultPrefs.getBoolean("Waza_be",true))
						url += "%20OR%20Waza_Be";
					
					
					url += "&rpp=50";
					InputStream is = Snippets.DownloadJsonFromUrl(url,a);
					Gson gson = new Gson();
					final Reader reader = new InputStreamReader(is);
					final Tweets tweets = gson.fromJson(reader, Tweets.class);
					Looper.prepare();
					Toast.makeText(a, ""+tweets.results.size(), Toast.LENGTH_LONG).show();
					Log.i("","6");
					a.runOnUiThread(new Thread(new Runnable() {
						public void run() {
							Log.i("","OK");
							l.setAdapter(new TweetItemAdapter(a,
									R.layout.row_tweet, tweets.results));
							Log.i("","OK");
						}
					}));
				} catch (Exception e) {
					e.printStackTrace();
					a.runOnUiThread(new Thread(new Runnable() {
						public void run() {
							//TextView tv = (TextView) a.findViewById(R.id.fail);
							//tv.setVisibility(View.VISIBLE);
							Toast.makeText(a, "No Tweets", Toast.LENGTH_LONG).show();

						}
					}));

				}

			}
		}).start();

	}

}