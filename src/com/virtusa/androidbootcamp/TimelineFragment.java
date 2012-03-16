package com.virtusa.androidbootcamp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TimelineFragment extends ListFragment implements
		SimpleCursorAdapter.ViewBinder {

	private static final String[] FROM = { StatusProvider.KEY_USER,
			StatusProvider.KEY_MESSAGE, StatusProvider.KEY_CREATED_AT };
	private static final int[] TO = { R.id.data_user, R.id.data_msg,
			R.id.data_date };
	private Cursor mCursor;
	private SimpleCursorAdapter mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		try {
			mCursor = getActivity().getContentResolver().query(
					StatusProvider.CONTENT_URI, null, null, null, null);
		} catch (Exception e) {
			// Something went wrong
		}

		mAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.timeline_row, mCursor, FROM, TO, 0);
		mAdapter.setViewBinder(this);
		setListAdapter(mAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		mCursor.requery();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPause() {
		super.onPause();
		mCursor.deactivate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mCursor.close();
	}

	@Override
	public boolean setViewValue(View view, Cursor cursor, int index) {
		int id = view.getId();
		switch (id) {
		case R.id.data_date:
			TextView date = (TextView) view;
			Long datelong = cursor.getLong(index);
			date.setText(DateUtils.getRelativeTimeSpanString(datelong));
			return true;

		default:
			return false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.options_timeline_fragment, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		switch (id) {
		case R.id.menu_refresh:
			intent = new Intent(getActivity(), UpdateService2.class);
			getActivity().startService(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
