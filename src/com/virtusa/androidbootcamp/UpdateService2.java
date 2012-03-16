package com.virtusa.androidbootcamp;

import java.util.Date;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class UpdateService2 extends IntentService {

	private static final String TAG = "UpdateService2";

	public UpdateService2() {
		super("UpdaterService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		Log.v(TAG, "onHandleIntent() invoked");
		YambaApplication app = YambaApplication.getInstance();
		try {
			ContentValues values = new ContentValues();
			List<Twitter.Status> hometimeline = app.getTwitter()
					.getHomeTimeline();
			for (Twitter.Status status : hometimeline) {
				long id = status.getId();
				String name = status.user.name;
				String msg = status.getText();
				values.clear();
				Date createdAt = status.getCreatedAt();
				values.put(StatusProvider.KEY_ID, id);
				values.put(StatusProvider.KEY_USER, name);
				values.put(StatusProvider.KEY_MESSAGE, msg);
				values.put(StatusProvider.KEY_CREATED_AT, createdAt.getTime());
				
				Log.i(TAG, "Post : " + id + " : screen name :" + name
						+ " message : " + msg + " created at : " + createdAt);
				try {
					getContentResolver().insert(StatusProvider.CONTENT_URI, values);
				} catch (SQLException e) {
					//ignoring , assuming that the row already exist.
				}
			}
		} catch (TwitterException e) {
			Log.v(TAG, "Unable to fetch home time line");
		} catch (SQLiteException e) {
			Log.v(TAG, "Unable to open timeline database");
		}
	}

}
