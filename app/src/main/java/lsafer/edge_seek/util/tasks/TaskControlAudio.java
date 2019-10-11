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

import android.content.Context;
import android.media.AudioManager;

import lsafer.edge_seek.util.abst.Task;

/**
 * A task controls audio when invoked.
 *
 * @author LSaferSE
 * @version 1 alpha (09-Oct-19)
 * @since 09-Oct-19
 */
final public class TaskControlAudio extends Task {
	@Override
	public void accept(Context context, Integer integer) {
		AudioManager am = context.getSystemService(AudioManager.class);
		assert am != null;

		integer += am.getStreamVolume(this.overlay.edge.stream);
		integer = integer > this.edge.maximum ? this.edge.maximum : integer < this.edge.minimum ? this.edge.minimum : integer;

		am.setStreamVolume(this.overlay.edge.stream, integer, 0);
		this.overlay.toast(integer);
	}
}
