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
package lsafer.edge_seek.view.global.perference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.Edge;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings({"unused", "JavaDoc"})
public class BooleanEPVA extends EdgePerformanceViewAdapter<Boolean> {
	public BooleanEPVA(Context context, ViewGroup parent0, ViewGroup parent1, Class<?> type, Edge edge, Object key) {
		super(context, parent0, parent1, type, edge, key);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_epva_boolean, null);
		CheckBox box = view.findViewById(R.id.value);

		view.<TextView>findViewById(R.id.title).setText(this.resources[0].equals("#") ? String.valueOf(this.key) : this.resources[0]);
		view.<TextView>findViewById(R.id.description).setText(this.resources[1].equals("#") ? "Custom" : this.resources[1]);

		box.setChecked(this.get());
		box.setOnClickListener(v -> this.set(((CheckBox) v).isChecked()));

		parent.addView(view);
		return view;
	}
}
