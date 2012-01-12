package com.profete162.WebcamWallonnes.Twitter;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.profete162.WebcamWallonnes.Twitter.Tweets.Tweet;


public class TwitterFragment extends ListFragment {
	String url = "";
	ImageView image = null;
	static ArrayList<Tweet> tweets = new ArrayList<Tweet>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Twitter.getTweets(this, this.getListView());
		/*String[] items = { "Ring sud de Bruxelles",
			"Autoroutes autour de Li�ge","Mons", "Axe Charleroi - Li�ge",
			"Axe Bruxelles - Namur", "Charleroi Est", "Charleroi Ouest",
			"Axe Namur - Arlon", "Anvers/Antwerpen", "Bruxelles/Brussels", 
			"Ring Bruxelles/Brussels", "Gand/Gent", "Lummen" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (
				getActivity(), android.R.layout.simple_list_item_1);
		for (String i:items){
			adapter.add(i);
		}
		this.setListAdapter(adapter);
		*/
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