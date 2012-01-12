package com.profete162.WebcamWallonnes.Twitter;

import java.io.File;
import java.util.ArrayList;

import android.support.v4.app.ListFragment;
import android.widget.ImageView;

import com.profete162.WebcamWallonnes.Twitter.Tweets.Tweet;


public class TwitterFragment extends ListFragment {
	String url = "";
	ImageView image = null;
	static ArrayList<Tweet> tweets = new ArrayList<Tweet>();


	@Override
	public void onResume() {
		super.onResume();
		Twitter.getTweets(getActivity(), this.getListView());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			File file = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"data/BeRoads");
			File[] files = file.listFiles();
			for (File f : files)
				f.delete();
		} catch (Exception e) {
		}
	}

}