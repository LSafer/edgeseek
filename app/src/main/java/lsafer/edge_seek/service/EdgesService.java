/*
 * Copyright (c) 2019, LSafer, All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * -You can edit this file (except the header).
 * -If you have change anything in this file. You
 *  shall mention that this file has been edited.
 *  By adding a new header (at the bottom of this header)
 *  with the word "Editor" on top of it.
 */
package lsafer.edge_seek.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.io.Edge;
import lsafer.edge_seek.util.Common;
import lsafer.edge_seek.view.global.EdgeOverlay;

/**
 * @author LSaferSE
 * @version 1 alpha (05-Oct-19)
 * @since 05-Oct-19
 */
@SuppressWarnings("JavaDoc")
public class EdgesService extends Service {
	final public static String CHANNEL_ID = "Edges Service";
	final public static ArrayList<EdgeOverlay> overlays = new ArrayList<>();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Edges Is Ready", NotificationManager.IMPORTANCE_MIN);
			channel.setDescription("Notify you that edges is activated");
			//noinspection ConstantConditions
			this.getSystemService(NotificationManager.class).createNotificationChannel(channel);
		}

		this.startForeground(1, new NotificationCompat.Builder(this, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Edges")
				.setContentText("Your screen's edges is ready to receive your finger touches!")
				.setPriority(NotificationCompat.PRIORITY_MIN)
				.build());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(App.load(this).main.activated) {
			App.edges.forEach((key, edge) -> {
				if (edge instanceof Edge) {
					EdgeOverlay overlay = ((Edge) edge).overlay(this);
					overlay.show();
					EdgesService.overlays.add(overlay);
				}
			});
			if (App.main.auto_brightness)
				this.registerReceiver(new Common.FreeReceiver(()->
						Settings.System.putInt(this.getContentResolver(),
								Settings.System.SCREEN_BRIGHTNESS_MODE,
								Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
				), new IntentFilter(Intent.ACTION_SCREEN_OFF));
			return Service.START_STICKY;
		} else {
			this.stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EdgesService.overlays.forEach(EdgeOverlay::dismiss);
		EdgesService.overlays.clear();
	}
}
