package lsafer.edge_seek.receiver;

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
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * A free receiver to be used on services.
 *
 * @author LSaferSE
 * @version 1 alpha (11-cct-2019)
 * @since 11-oct-2019
 */
final public class FreeReceiver extends BroadcastReceiver {
	/**
	 * The code to run when this receiver receives an action.
	 */
	private Runnable runnable;

	/**
	 * Initialize this.
	 *
	 * @param runnable to be run when this receiver receives an action
	 */
	public FreeReceiver(Runnable runnable) {
		this.runnable = runnable;
	}

	@SuppressLint("UnsafeProtectedBroadcastReceiver")
	@Override
	public void onReceive(Context context, Intent intent) {
		this.runnable.run();
	}
}
