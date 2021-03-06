package com.profete162.WebcamWallonnes.radar;

@SuppressWarnings("unchecked")
public class Radar implements Comparable {

	private String station;
	private double lat;
	private double lon;
	private String distance;
	private int type;
	private int speedLimit;
	private int id;

	public Radar(String station, double lat, double lon, String distance,
			int speedLimit, int type, int id) {
		this.station = station;
		this.lat = lat;
		this.lon = lon;
		this.distance = distance;
		this.speedLimit = speedLimit;
		this.type = type;
		this.id = id;

	}

	public String getRadar() {
		return station;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public int getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public String getDistance() {
		return distance;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public int compareTo(Object toCompare) {
		Radar otherStation = (Radar) toCompare;
		return Double.compare(Double.valueOf(this.distance),
				Double.valueOf(otherStation.getDistance()));

	}

}
