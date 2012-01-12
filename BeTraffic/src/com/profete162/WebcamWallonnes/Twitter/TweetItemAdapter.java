package com.profete162.WebcamWallonnes.Twitter;

import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.profete162.WebcamWallonnes.R;
import com.profete162.WebcamWallonnes.Twitter.Tweets.Tweet;

public class TweetItemAdapter extends ArrayAdapter<Tweet> {
	private ArrayList<Tweet> tweets;

    public ImageLoader imageLoader; 

	public TweetItemAdapter(Activity a, int textViewResourceId,
			ArrayList<Tweet> tweets) {
		super(a, textViewResourceId, tweets);
		this.tweets = tweets;
        imageLoader=new ImageLoader(a);
	}
	
	 public int getCount() {
	        return tweets.size();
	    }

	    public Tweet getItem(int position) {
	        return tweets.get(position);
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    
	    public static class ViewHolder{
	        public TextView username;
	        public TextView message;
	        public ImageView image;
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			//LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LayoutInflater vi = (LayoutInflater) super.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_tweet, null);
            holder=new ViewHolder();
            holder.username = (TextView) v.findViewById(R.id.username);
            holder.message = (TextView) v.findViewById(R.id.message);
            holder.image = (ImageView) v.findViewById(R.id.avatar);
            v.setTag(holder);
		}
        else
            holder=(ViewHolder)v.getTag();

		final Tweet tweet = tweets.get(position);
		if (tweet != null) {
			holder.username.setText(tweet.from_user);
			holder.message.setText(tweet.text);
	        holder.image.setTag(tweet.profile_image_url);
	        imageLoader.DisplayImage(tweet.profile_image_url, super.getContext(), holder.image);

		}
		return v;
	}

	
	public static Bitmap getBitmap(String bitmapUrl) {
		  try {
		    URL url = new URL(bitmapUrl);
		    return BitmapFactory.decodeStream(url.openConnection().getInputStream()); 
		  }
		  catch(Exception ex) {return null;}
		}
}
