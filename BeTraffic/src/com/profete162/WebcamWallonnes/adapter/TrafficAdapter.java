package com.profete162.WebcamWallonnes.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.profete162.WebcamWallonnes.R;
import com.profete162.WebcamWallonnes.TrafficActivity.Traffic;


public class TrafficAdapter extends ArrayAdapter<Traffic>{

	private LayoutInflater myLayoutInflater;
	protected List<Traffic> items;
	double currentLat; double currentLon;
	
	public TrafficAdapter(Context context, int textViewResourceId,List<Traffic> list,LayoutInflater layoutInflater,double currentLat, double currentLon) {
		super(context, textViewResourceId, list);
		this.myLayoutInflater = layoutInflater;
		this.items = list;
		this.currentLat = currentLat;
		this.currentLon = currentLon;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//

		View row = convertView;

		if (row == null) {			
			row = myLayoutInflater.inflate(R.layout.row_traffic, parent, false);
		}

		TextView tMax = (TextView) row.findViewById(R.id.tname);
		tMax.setText(items.get(position).getLocation());
		
		TextView tMin = (TextView) row.findViewById(R.id.tsource);
		tMin.setText(items.get(position).getSource());
		
		TextView tDis = (TextView) row.findViewById(R.id.tdist);
		tDis.setText(items.get(position).getDistance() + "km");

		

		 return row;
	}
}
	

