package com.virtusa.androidbootcamp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import winterwell.jtwitter.*;
import winterwell.jtwitter.Twitter.Status;

public class LoginActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logonui);
		Button loginCilck = (Button) findViewById(R.id.LoginClickButton);

		loginCilck.setOnClickListener(this);
		

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.LoginClickButton:
			EditText usernameText = (EditText)findViewById(R.id.usernameEditText);
			EditText passwordText = (EditText)findViewById(R.id.passwordEditText);
			SharedPreferences pref = getSharedPreferences("JTWITTER_LOGIN_DETAILS", MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = pref.edit();
			prefEditor.putString("JTWITTER_USERNAME", usernameText.getText().toString());
			prefEditor.putString("JTWITTER_PASSWORD", passwordText.getText().toString());
			prefEditor.commit();
			finish();
			break;
		default:
			break;
		}
	}

}
