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
package lsafer.edge_seek.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import lsafer.edge_seek.io.App;
import lsafer.edge_seek.service.EdgesService;

/**
 * @author LSaferSE
 * @version 1 alpha (09-Oct-19)
 * @since 09-Oct-19
 */
public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null && intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) && App.load(context).main.boot && App.main.activated)
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
				context.startForegroundService(new Intent(context, EdgesService.class));
			else context.startService(new Intent(context, EdgesService.class));
	}
}
