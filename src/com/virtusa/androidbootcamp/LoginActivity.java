package com.virtusa.androidbootcamp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import winterwell.jtwitter.*;
import winterwell.jtwitter.Twitter.Status;

public class LoginActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logonui);
		Button loginCilck = (Button) findViewById(R.id.LoginClickButton);
		Button publicTimeline = (Button) findViewById(R.id.publicTimelineButton);
		publicTimeline.setOnClickListener(this);
		loginCilck.setOnClickListener(this);
		

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.LoginClickButton:
			finish();
			break;
		case R.id.publicTimelineButton:
			Intent intent = new Intent(LoginActivity.this,
					PublicTimelineActivity.class);
			startActivity(intent);

		default:
			break;
		}
	}

}
