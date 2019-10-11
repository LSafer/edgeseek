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

import lsafer.edge_seek.util.abst.Task;

/**
 * A task do nothing when invoked.
 *
 * @author LSaferSE
 * @version 1 alpha (09-Oct-19)
 * @since 09-Oct-19
 */
final public class TaskNothing extends Task {
	@Override
	public void accept(Context context, Integer integer) {
	}
}
