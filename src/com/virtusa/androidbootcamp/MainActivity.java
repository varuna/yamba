package com.virtusa.androidbootcamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	private ComposeFragment mComposeFragment;
	private TimelineFragment mTimelineFragment;
	private FragmentManager mFragmentManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mFragmentManager = getSupportFragmentManager();
		
		if (savedInstanceState == null) {
			// setting up fragments
			mComposeFragment = new ComposeFragment();
			mTimelineFragment = new TimelineFragment();

			// Adding fragments to the layout, and hide the mComposeFragment

			mFragmentManager
					.beginTransaction()
					.add(R.id.fragment_container, mComposeFragment, "compose")
					.add(R.id.fragment_container, mTimelineFragment, "timeline")
					.hide(mComposeFragment).commit();
		}else{
			//if activity bounced find the fragments;
			mComposeFragment = (ComposeFragment)mFragmentManager.findFragmentByTag("compose");
			mTimelineFragment = (TimelineFragment)mFragmentManager.findFragmentByTag("timeline");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.options_main, menu);
		mi.inflate(R.menu.option_main_fragment_control, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		switch (id) {
		case R.id.menu_prefences:
			intent = new Intent(this, PrefsActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_post:
			toggleFragmentVisibility(mComposeFragment, mTimelineFragment);
			return true;
		case R.id.menu_timeline:
			toggleFragmentVisibility(mTimelineFragment, mComposeFragment);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	protected void onDestroy() {
		// Stopping the worker thread in updateService version 1 :D
		Intent intent = new Intent(this, UpdateService.class);
		stopService(intent);
		super.onDestroy();

	}
	
	private void toggleFragmentVisibility(Fragment toShow, Fragment toHide)
	{
		mFragmentManager.beginTransaction()
			.hide(toHide)
			.show(toShow)
			.commit();
	}

}