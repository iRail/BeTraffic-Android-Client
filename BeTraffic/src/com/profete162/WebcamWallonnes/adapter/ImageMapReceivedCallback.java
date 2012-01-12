package com.profete162.WebcamWallonnes.adapter;

import com.profete162.WebcamWallonnes.CamerasActivity;

public interface ImageMapReceivedCallback
{
	// Called when an image is rendered
	public void onImageReceived(CamerasActivity.ImageDisplayer displayer);
}