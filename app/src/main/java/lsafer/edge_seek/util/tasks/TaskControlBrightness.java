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
package lsafer.edge_seek.util.tasks;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import lsafer.edge_seek.util.abst.Task;

/**
 * A task controls brightness when invoked.
 *
 * @author LSaferSE
 * @version 1 alpha (09-Oct-19)
 * @since 09-Oct-19
 */
final public class TaskControlBrightness extends Task {
	@Override
	public void accept(Context context, Integer integer) {
		try {
			ContentResolver resolver = context.getContentResolver();

			integer += Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
			integer = integer > this.edge.maximum ? this.edge.maximum : integer < this.edge.minimum ? this.edge.minimum : integer;

			Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, integer);

			this.overlay.toast(integer);
		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
		}
	}
}
