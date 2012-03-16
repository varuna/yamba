package com.virtusa.androidbootcamp;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApplication extends Application implements
		OnSharedPreferenceChangeListener {

	private static YambaApplication instance;
	private static final String TAG = "YambaApplication";
	private Twitter t;
	private SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		new TimelineHelper(this);
	}

	public static YambaApplication getInstance() {
		return instance;
	}

	public synchronized Twitter getTwitter() {
		if (t == null) {
			t = new Twitter(prefs.getString("PREF_USER", null),
					prefs.getString("PREF_PASSWORD", null));
			t.setAPIRootUrl(prefs.getString("PREF_SITE_URL",
					"http://yamba.marakana.com/api"));
		}
		return t;
	}

	@Override
	public synchronized void onSharedPreferenceChanged(
			SharedPreferences sharedPreferences, String key) {
		Log.v(TAG, "Shared preferences changed : " + key);
		t = null;
	}

}
