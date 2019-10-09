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
package lsafer.edge_seek.view.global;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings({"unused", "WeakerAccess", "JavaDoc"})
public class SingleToast extends Toast {
	private static SingleToast current;

	@SuppressLint("InflateParams")
	public SingleToast(Context context) {
		super(context);
	}

	public SingleToast(Context context, View view) {
		super(context);
		this.setView(view);
	}

	@Override
	public void show() {
		if (SingleToast.current != null)
			SingleToast.current.cancel();
		SingleToast.current = this;
		super.show();
	}
}
