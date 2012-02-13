package com.profete162.WebcamWallonnes.misc;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ItemizedOverlayPerso extends ItemizedOverlay<OverlayItem> {

	private List<GeoPoint> points = new ArrayList<GeoPoint>();

	public ItemizedOverlayPerso(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	@Override
	protected OverlayItem createItem(int i) {
		GeoPoint point = points.get(i);
		return new OverlayItem(point, "Title", "Description");
	}

	@Override
	public int size() {
		return points.size();
	}

	public void addPoint(GeoPoint point) {
		this.points.add(point);
		populate();
	}

	public void clearPoint() {
		this.points.clear();
		populate();
	}

	//
	// method for events when the user clicks on any marker ...
	//
	@Override
	protected boolean onTap(int index) {
		return true;
	}
}