package com.virtusa.androidbootcamp;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
		OnKeyListener {

	/** Called when the activity is first created. */
	private static final String TAG = "MainActivty";
	private EditText characterSet;
	private Toast mToast;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.v(TAG, "onCreate() invoked");
		characterSet = (EditText) findViewById(R.id.StatusEditText);
		TextView charCountDisp = (TextView) findViewById(R.id.CharacterCountTextView);
		Button postButton = (Button) findViewById(R.id.PostTweetButton);
		postButton.setOnClickListener(this);
		charCountDisp.setText("remaining : 140");
		characterSet.setOnKeyListener(this);
		mToast = Toast.makeText(this, null, Toast.LENGTH_LONG);

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.v(TAG, String.valueOf(keyCode));
		EditText charSet = (EditText) v;
		TextView charCount = (TextView) findViewById(R.id.CharacterCountTextView);
		int maxCharacters = 140;
		int currentCount = maxCharacters - charSet.getText().length();
		if (currentCount > 0)
			charCount.setText("remaining : " + String.valueOf(currentCount));
		else {
			charCount.setTextColor(Color.RED);
			charCount.setBackgroundColor(Color.WHITE);
			charCount.setText(" Message too long, remove : "
					+ String.valueOf(currentCount) + " Characters ");
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.PostTweetButton:
			String msg = characterSet.getText().toString();
			if (!TextUtils.isEmpty(msg)) {
				PostToTwitter ptt = new PostToTwitter();
				ptt.execute(msg);
				characterSet.setText("");
				TextView charCountDisp = (TextView) findViewById(R.id.CharacterCountTextView);
				charCountDisp.setText("remaining : 140");
			}
			break;
		default:
			break;
		}

	}

	private class PostToTwitter extends AsyncTask<String, Void, Integer> {
		private Twitter t;

		@Override
		protected Integer doInBackground(String... params) {
			int result = R.string.post_success;
			t = new Twitter("student", "password");
			t.setAPIRootUrl("http://yamba.marakana.com/api");
			try {
				t.setStatus(params[0]);
			} catch (TwitterException e) {
				Log.w(TAG, "Failed to post"+ e.getMessage(), e);
				result = R.string.post_fail;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			mToast.setText(result);
			mToast.show();
		}

	}
}