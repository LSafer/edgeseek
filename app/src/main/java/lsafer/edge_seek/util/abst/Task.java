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
package lsafer.edge_seek.util.abst;

import android.content.Context;

import java.util.function.BiConsumer;

import lsafer.edge_seek.constant.Constant;
import lsafer.edge_seek.io.Edge;
import lsafer.edge_seek.util.tasks.TaskControlAudio;
import lsafer.edge_seek.util.tasks.TaskControlBrightness;
import lsafer.edge_seek.util.tasks.TaskExpandStatusbar;
import lsafer.edge_seek.util.tasks.TaskNothing;
import lsafer.edge_seek.view.EdgeOverlay;

/**
 * A task abstract.
 *
 * @author LSaferSE
 * @version 1 alpha (09-Oct-19)
 * @since 09-Oct-19
 */
public abstract class Task implements BiConsumer<Context, Integer> {
	/**
	 * The {@link EdgeOverlay} attached to this.
	 */
	protected EdgeOverlay overlay;
	/**
	 * The {@link Edge} attached to this.
	 */
	protected Edge edge;

	/**
	 * Attach this task to the given overlay.
	 *
	 * @param overlay to be attached to
	 * @return this
	 */
	public Task attach(EdgeOverlay overlay) {
		this.overlay = overlay;
		this.edge = this.overlay.edge;
		return this;
	}

	/**
	 * Switch for a task matches the given string.
	 *
	 * @param string to switch for
	 * @return a task matches the given string
	 */
	public static Task newFor(String string) {
		switch(string) {
			case Constant.TASK_CONTROL_BRIGHTNESS:
				return new TaskControlBrightness();
			case Constant.TASK_CONTROL_AUDIO:
				return new TaskControlAudio();
			case Constant.TASK_EXPAND_STATUSBAR:
				return new TaskExpandStatusbar();
			default:
				return new TaskNothing();
		}
	}
}
