package com.profete162.WebcamWallonnes.Weather;


public class Weather {
	private String tMax = null;
	private String tMin = null;
	private String winSpeed = null;
	private String windDir = null;
	private int code=0;
	private String desc=null;
	
	public Weather(String desc,String tMax, String tMin, String winSpeed, String windDir, int code) {
		this.tMax = tMax;
		this.tMin = tMin;
		this.winSpeed = winSpeed;
		this.windDir = windDir;
		this.code = code;
		this.desc = desc;

	}
	
	public String getTMax() {
		return tMax;
	}
	public String getTMin() {
		return tMin;
	}
	public String getWinSpeed() {
		return winSpeed;
	}
	public String getWindDir() {
		return windDir;
	}
	public int getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	
}
