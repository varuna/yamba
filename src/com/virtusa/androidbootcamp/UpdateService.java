package com.virtusa.androidbootcamp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service implements Runnable{

	private static final String TAG = "UpdateService";
	private boolean isRunning = false;
	private Thread workerThread;
	@Override
	public IBinder onBind(Intent arg0) {
		// We don't implement this method
		Log.v(TAG, "onBind() invoked");
		return null;
	}

	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate() invoked");
		workerThread = new Thread(this);
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy() invoked");
		workerThread.interrupt();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "onStartCommand() invoked");
		if (!isRunning)
		{
			isRunning = true;
			workerThread.start();
		}
		return START_NOT_STICKY;
	}

	@Override
	public void run() {
		while(!Thread.interrupted())
		{
			Log.v(TAG, "Thread is running");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				break;
			}
		}
		Log.v(TAG, "Thread is shutting down");
	}
	
	

}
