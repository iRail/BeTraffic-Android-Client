package com.profete162.WebcamWallonnes.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.profete162.WebcamWallonnes.R;
import com.profete162.WebcamWallonnes.radar.Radar;

public class RadarLocationAdapter extends ArrayAdapter<Radar> {
	public RadarLocationAdapter(Context context, int rowResourceId,
			List<Radar> list) {
		super(context, rowResourceId, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) super.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_radar, null);
		}
		Radar station = getItem(position);
		if (station != null) {
			TextView tvName = (TextView) v.findViewById(R.id.tv_name);
			TextView tvGps = (TextView) v.findViewById(R.id.tv_speedlimit);
			TextView tvDistance = (TextView) v.findViewById(R.id.tv_dis);
			// ImageView ivExpress = (ImageView) v.findViewById(R.id.ivExpress);

			tvName.setText(station.getRadar());
			tvGps.setText(""+station.getSpeedLimit());

			int iDistance = (int) (Double.valueOf(station.getDistance()) / 100);
			tvDistance.setText((double) iDistance / 10 + "km");
			/*
			 * switch(station.getType()){ case 1:
			 * ivExpress.setImageResource(R.drawable.express); break;
			 * 
			 * case 2: ivExpress.setImageResource(R.drawable.esso); break;
			 * 
			 * case 3: ivExpress.setImageResource(R.drawable.shell); break; }
			 */

		}
		return v;
	}



}
