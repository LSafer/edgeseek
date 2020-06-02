/*
 *	Copyright 2020 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package lsafer.edgeseek.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.view.WindowManager;

import androidx.core.app.NotificationCompat;

import java.util.Arrays;
import java.util.List;

import lsafer.edgeseek.App;
import lsafer.edgeseek.Edge;
import lsafer.edgeseek.R;
import lsafer.edgeseek.receiver.ScreenOffBroadCastReceiver;

/**
 * A service that responsible to display the edges customized by this application.
 * The service takes its data directly from {@link App#data}.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
public class MainService extends Service {
	/**
	 * The edges served by this service.
	 */
	private List<Edge> edges;
	/**
	 * A registered receiver on this service.
	 */
	private ScreenOffBroadCastReceiver screenOffReceiver;
	/**
	 * The window-manager that this service is using.
	 */
	private WindowManager wm;

	@Override
	public void onCreate() {
		super.onCreate();

		//foreground-service notification
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			this.getSystemService(NotificationManager.class)
					.createNotificationChannel(new NotificationChannel(
							"Foreground",
							"Foreground Service Channel",
							NotificationManager.IMPORTANCE_LOW
					));
		this.startForeground(1, new NotificationCompat.Builder(this, "Foreground")
				.setContentTitle("Running in the background")
				.setContentText("")
				.setSmallIcon(R.drawable.ic_sync)
				.build());

		//stop if not activated
		if (!App.data.activated) {
			this.stopSelf();
			return;
		}

		//define the window manager
		this.wm = this.getSystemService(WindowManager.class);

		//construct the edges
		this.edges = Arrays.asList(
				new Edge(this, this.wm, App.data.edges.get(0)),
				new Edge(this, this.wm, App.data.edges.get(1)),
				new Edge(this, this.wm, App.data.edges.get(2)),
				new Edge(this, this.wm, App.data.edges.get(3))
		);

		//start the edges
		this.edges.forEach(Edge::start);

		//register on-screen-off receiver
		this.screenOffReceiver = new ScreenOffBroadCastReceiver();
		this.registerReceiver(this.screenOffReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//stop if not activated
		if (!App.data.activated) {
			this.stopSelf();
			return startId;
		}

		//refresh edges managers
		this.edges.forEach(Edge::update);

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		//destroy the edges
		if (this.edges != null)
			this.edges.forEach(Edge::destroy);

		//unregister on-screen-off receiver
		if (this.screenOffReceiver != null)
			this.unregisterReceiver(this.screenOffReceiver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
