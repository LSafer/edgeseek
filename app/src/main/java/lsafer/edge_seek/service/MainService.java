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
import android.content.Context;
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
import lsafer.edge_seek.receiver.FreeReceiver;
import lsafer.edge_seek.view.EdgeOverlay;
import lsafer.util.impl.FolderHashMap;

/**
 * A service to start this application services.
 *
 * @author LSaferSE
 * @version 1 alpha (05-Oct-19)
 * @since 05-Oct-19
 */
final public class MainService extends Service {
	/**
	 * The ID of this services's foreground notification.
	 */
	final public static String CHANNEL_ID = "Main Service";

	/**
	 * Currently displayed overlays from this service.
	 */
	final public ArrayList<EdgeOverlay> overlays = new ArrayList<>();

	/**
	 * The registered receiver to receive screen-off action.
	 */
	public FreeReceiver screenoff_receiver;

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

		App.edges.clear();
		if(App.init(this).main.<App.Main>load().activated) {
			App.edges.<FolderHashMap<?, ?>>load().forEach((key, edge) -> {
				if (edge instanceof Edge)
					this.overlays.add(((Edge) edge).overlay(this).show());
			});
			if (App.main.auto_brightness)
				this.registerReceiver(screenoff_receiver = new FreeReceiver(()->
						Settings.System.putInt(this.getContentResolver(),
								Settings.System.SCREEN_BRIGHTNESS_MODE,
								Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
				), new IntentFilter(Intent.ACTION_SCREEN_OFF));
		} else {
			this.stopSelf();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (!App.main.<App.Main>load().activated)
			this.stopSelf();

		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.overlays.forEach(EdgeOverlay::dismiss);
		this.unregisterReceiver(this.screenoff_receiver);
	}

	/**
	 * Start the service.
	 *
	 * @param context to use to start
	 */
	public static void start(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			context.startForegroundService(new Intent(context, MainService.class));
		else context.startService(new Intent(context, MainService.class));
	}

	/**
	 * Restart the service.
	 *
	 * @param context to use to restart
	 */
	public static void restart(Context context) {
		MainService.stop(context);
		MainService.start(context);
	}

	/**
	 * Stop the service.
	 *
	 * @param context to use to stop
	 */
	public static void stop(Context context) {
		context.stopService(new Intent(context, MainService.class));
	}
}
