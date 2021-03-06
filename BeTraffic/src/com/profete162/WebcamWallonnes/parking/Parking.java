package com.profete162.WebcamWallonnes.parking;

public class Parking implements Comparable<Object>  {

	private String station;
	private double lat;
	private double lon;
	private String distance;
	private String adress;
	private int type;
	private int id;

	public Parking(String station,double lat, double lon, String distance, String adress, int type, int id) {
		this.station = station;
		this.lat = lat;
		this.lon = lon;
		this.distance = distance;
		this.adress = adress;
		this.type = type;
		this.id=id;

	}

	public String getStation() {
		return station;
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
	
	public String getAdress() {
		return adress;
	}

	public int compareTo(Object toCompare) {
		Parking otherStation=(Parking)toCompare;
	    return  Double.compare(Double.valueOf(this.distance),Double.valueOf(otherStation.getDistance()));

	}

}
