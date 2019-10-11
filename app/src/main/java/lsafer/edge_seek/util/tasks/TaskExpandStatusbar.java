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

import android.annotation.SuppressLint;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;

import lsafer.edge_seek.util.abst.Task;

/**
 * A task expands statusbar when invoked.
 *
 * @author LSaferSE
 * @version 1 alpha (09-Oct-19)
 * @since 09-Oct-19
 */
final public class TaskExpandStatusbar extends Task {
	@SuppressLint("WrongConstant")
	@SuppressWarnings("JavaReflectionMemberAccess")
	@Override
	public void accept(Context context, Integer integer) {
		try {
			Class.forName("android.app.StatusBarManager")
					.getMethod("expandNotificationsPanel")
					.invoke(context.getSystemService("statusbar"));
		} catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
