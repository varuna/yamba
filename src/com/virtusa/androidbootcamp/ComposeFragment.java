package com.virtusa.androidbootcamp;

import winterwell.jtwitter.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeFragment extends Fragment implements OnClickListener,
		OnKeyListener {
	private static final String TAG = "ComposeFragment";
	private EditText characterSet;
	private Toast mToast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View top = inflater.inflate(R.layout.composefragment, container, false);

		characterSet = (EditText) top.findViewById(R.id.StatusEditText);
		TextView charCountDisp = (TextView) top
				.findViewById(R.id.CharacterCountTextView);
		Button postButton = (Button) top.findViewById(R.id.PostTweetButton);
		postButton.setOnClickListener(this);
		charCountDisp.setText("remaining : 140");
		characterSet.setOnKeyListener(this);
		mToast = Toast.makeText(getActivity().getApplicationContext(), null,
				Toast.LENGTH_LONG);
		return top;
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
				// TextView charCountDisp = (TextView)
				// findViewById(R.id.CharacterCountTextView);
				// charCountDisp.setText("remaining : 140");
			}
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.v(TAG, String.valueOf(keyCode));
//		EditText charSet = (EditText) v;
//		TextView charCount = (TextView)findViewById(R.id.CharacterCountTextView);
//		int maxCharacters = 140;
//		int currentCount = maxCharacters - charSet.getText().length();
//		if (currentCount > 0)
//			charCount.setText("remaining : " + String.valueOf(currentCount));
//		else {
//			charCount.setTextColor(Color.RED);
//			charCount.setBackgroundColor(Color.WHITE);
//			charCount.setText(" Message too long, remove : "
//					+ String.valueOf(currentCount) + " Characters ");
//		}
		return false;
	}

	private class PostToTwitter extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			int result = R.string.post_success;

			try {
				YambaApplication.getInstance().getTwitter()
						.setStatus(params[0]);
			} catch (TwitterException e) {
				Log.w(TAG, "Failed to post" + e.getMessage(), e);
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
