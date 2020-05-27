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
package lsafer.edgeseek.legacy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import lsafer.edgeseek.R;

/**
 * Two toasts of this class can't be displayed at the same time.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 08-Oct-2019
 */
final public class SingleToast extends Toast {
	/**
	 * The current displaying toast.
	 */
	private static SingleToast current;

	/**
	 * Constructs a new toast.
	 *
	 * @param context to be used
	 */
	public SingleToast(Context context) {
		super(context);
	}

	@Override
	public void show() {
		if (SingleToast.current != null)
			SingleToast.current.cancel();
		SingleToast.current = this;
		super.show();
	}

	/**
	 * relevant to the method {@link Toast#makeText(Context, CharSequence, int)}.
	 *
	 * @param context to be used
	 * @param string  to be displayed
	 * @param duration to show the toast within
	 * @return the toast constructed
	 */
	@SuppressLint("InflateParams")
	public static SingleToast makeText(Context context, String string, int duration) {
		SingleToast toast = new SingleToast(context);

		toast.setDuration(duration);
		View view = LayoutInflater.from(context).inflate(R.layout.legacy_toast, null);
		view.<TextView>findViewById(R.id.txt).setText(string);

		toast.setView(view);
		return toast;
	}
}