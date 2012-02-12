package com.profete162.WebcamWallonnes;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.profete162.WebcamWallonnes.TrafficActivity.Traffic;
import com.profete162.WebcamWallonnes.TrafficActivity.TrafficList;
import com.profete162.WebcamWallonnes.adapter.TrafficAdapter;

public class TrafficFragment extends ListFragment {
	TrafficList obj;
	static double GPS[];

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Runnable trainSearch = new Runnable() {

			public void run() {
				final String parsed = parseTraffic();

				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						getSupportActivity().getSupportActionBar().setTitle(
								parsed);
						setListAdapter(new TrafficAdapter(getActivity(),
								R.layout.row_traffic, obj.getTraffics()
										.getItems(), getActivity()
										.getLayoutInflater(), GPS[0], GPS[1]));
					}
				});

			}
		};

		Thread thread = new Thread(null, trainSearch, "MyThread");
		thread.start();

	}

	public String parseTraffic() {

		try {

			String url = "http://data.beroads.com/IWay/TrafficEvent/"
					+ getString(R.string.lan) + "/all.json?max=50&from="
					+ GPS[0] + "," + GPS[1] + "&area=100";
			System.out.println("*** URL:" + url);

			try {

				// Log.i("MY INFO", "Json Parser started..");
				Gson gson = new Gson();
				Reader r = new InputStreamReader(getJSONData(url));
				// Log.i("MY INFO", r.toString());
				obj = gson.fromJson(r, TrafficList.class);

			} catch (Exception ex) {
				ex.printStackTrace();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL";
		}

		return "OK";

	}

	public InputStream getJSONData(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter("http.useragent",
				" Appli de Waza_be " + System.getProperty("http.agent"));
		URI uri;
		InputStream data = null;
		try {
			uri = new URI(url);
			HttpGet method = new HttpGet(uri);
			HttpResponse response = httpClient.execute(method);
			data = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		TrafficAdapter adapter = (TrafficAdapter) l.getAdapter();

		Traffic clickedItem = (Traffic) adapter.getItem(position);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(clickedItem.getDesc())
				.setCancelable(false)
				.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});

		builder.create().show();

	}
}
