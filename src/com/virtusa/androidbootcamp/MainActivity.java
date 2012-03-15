package com.virtusa.androidbootcamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
//		SharedPreferences pref = getSharedPreferences("JTWITTER_LOGIN_DETAILS",
//				MODE_PRIVATE);
//		if (pref.getString("JTWITTER_USERNAME", "error").equals("error")) {
//			Intent intent = new Intent(this, LoginActivity.class);
//			startActivity(intent);
//		}
		Button logoutButton = (Button) findViewById(R.id.logoutButton);
		logoutButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.options_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_prefences:
			Intent intent = new Intent(this, PrefsActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onClick(View v) {
		SharedPreferences pref = getSharedPreferences("JTWITTER_LOGIN_DETAILS",
				MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = pref.edit();
		prefEditor.putString("JTWITTER_USERNAME","");
		prefEditor.putString("JTWITTER_PASSWORD","");
		prefEditor.commit();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

}