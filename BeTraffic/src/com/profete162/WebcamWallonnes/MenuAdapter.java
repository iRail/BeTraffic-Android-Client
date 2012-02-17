package com.profete162.WebcamWallonnes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.TitleProvider;

public class MenuAdapter extends FragmentPagerAdapter implements TitleProvider {
	
	Context context;
	
	public MenuAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context=context;
		
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		return 2;
	}

	
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new SortAlphabet.AppListFragment();
			//return new AboutFragment();
		case 1:
			return new SortGroup.AppListFragment();
		}
		return null;
	}


	@Override
	public String getTitle(int position) {
		switch(position){
		case 0:
			return context.getString(R.string.alphabet);
		case 1:
			return context.getString(R.string.region);
		}
		return ""+position;
	}
}