package com.virtusa.androidbootcamp;

import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class PrefsActivity extends PreferenceActivity {

	private static final String TAG = "PrefsActivity"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
			addPreferencesFromResource(R.xml.prefs);
			Log.v(TAG,	"addPreferencesFromResource");
		}
	}

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.prefs_headers, target);
		Log.v(TAG,	"loadHeadersFromResource");
	}

}
