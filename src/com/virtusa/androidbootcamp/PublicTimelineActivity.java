package com.virtusa.androidbootcamp;

import java.util.Iterator;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PublicTimelineActivity extends Activity {

	private LinearLayout timeline;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Log.i("PublicTimelineActivity","starting up");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publictimeline);
		Twitter t = new Twitter();
		// Twitter t= new Twitter("student","password");
//		t.setAPIRootUrl("http://yamba.marakana.com/api");
		@SuppressWarnings("unused")
		List<Status> statuses = t.getPublicTimeline();
		timeline = (LinearLayout) findViewById(R.id.publicTimeLineView);
		
	}
	
	@SuppressWarnings("unused")
	private class PublicTimeLine extends AsyncTask<Void, Void, List<Status>>
	{

		@Override
		protected List<winterwell.jtwitter.Twitter.Status> doInBackground(Void... params) {
			
			Twitter t = new Twitter();
			// Twitter t= new Twitter("student","password");
//			t.setAPIRootUrl("http://yamba.marakana.com/api");
			List<winterwell.jtwitter.Twitter.Status> statuses = t.getPublicTimeline();
			return statuses;
		}

		@Override
		protected void onPostExecute(List<winterwell.jtwitter.Twitter.Status> result) {
			Iterator<winterwell.jtwitter.Twitter.Status> it = result.iterator();
			while (it.hasNext()) {
				winterwell.jtwitter.Twitter.Status stat = (winterwell.jtwitter.Twitter.Status) it.next();
				LinearLayout twit = new LinearLayout(PublicTimelineActivity.this);
				TextView screenName = new TextView(PublicTimelineActivity.this);
				TextView text = new TextView(PublicTimelineActivity.this);
				screenName.setText(stat.getUser().toString());
				text.setText(stat.getText().toString());
				twit.addView(screenName);
				twit.addView(text);
				timeline.addView(twit);
			}
		}
		
	}

}
