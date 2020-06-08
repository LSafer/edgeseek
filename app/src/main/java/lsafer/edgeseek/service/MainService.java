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

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import lsafer.edgeseek.App;
import lsafer.edgeseek.Edge;
import lsafer.edgeseek.R;
import lsafer.edgeseek.receiver.ScreenOffBroadCastReceiver;
import lsafer.edgeseek.util.Position;
import lsafer.edgeseek.util.Util;

/**
 * A service that responsible to display the edges customized by this application.
 * The service takes its data directly from {@link App#data}.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
final public class MainService extends Service {
	/**
	 * The edges served by this service.
	 */
	private List<Edge> edges;
	/**
	 * A registered receiver on this service.
	 */
	private ScreenOffBroadCastReceiver screenOffReceiver;

	@Override
	public void onCreate() {
		super.onCreate();

		//foreground-service notification
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel("main", this.getString(R.string._tit_noti_MAIN_SERVICE_CHANNEL), NotificationManager.IMPORTANCE_MIN);
			channel.setDescription(this.getString(R.string._des_noti_MAIN_SERVICE_CHANNEL));

			this.getSystemService(NotificationManager.class)
					.createNotificationChannel(channel);
			this.startForeground(1, new NotificationCompat.Builder(this, "main")
					.setContentTitle(this.getString(R.string._tit_noti_MAIN_SERVICE))
					.setContentText(this.getString(R.string._des_noti_MAIN_SERVICE))
					.setSmallIcon(R.drawable.ic_sync)
					.build());
		}

		//stop if not activated
		if (!App.data.activated) {
			this.stopSelf();
			return;
		}

		//construct the edges
		this.edges = Util.fill(new ArrayList(), 0, Position.MAX, i -> new Edge(this, App.data.sides.get(i), App.data.edges.get(i)));

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
			return START_NOT_STICKY;
		}

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
