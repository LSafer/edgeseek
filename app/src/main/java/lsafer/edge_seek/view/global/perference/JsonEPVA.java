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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.Edge;
import lsafer.json.JSON;
import lsafer.util.Caster;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings("JavaDoc")
public class JsonEPVA extends EdgePerformanceViewAdapter<Object> {
	public JsonEPVA(Context context, ViewGroup parent0, ViewGroup parent1, Class<?> type, Edge edge, Object key) {
		super(context, parent0, parent1, type, edge, key);
	}

	@Override
	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_epva_json, null);
		EditText value = view.findViewById(R.id.value);

		view.<TextView>findViewById(R.id.title).setText(this.resources[0].equals("#") ? String.valueOf(this.key) : this.resources[0]);
		view.<TextView>findViewById(R.id.description).setText(this.resources[1].equals("#") ? "Custom" : this.resources[1]);
		view.<TextView>findViewById(R.id.type).setText("Type: " + this.type.getSimpleName());

		value.setText(String.valueOf(this.get()));
		value.addTextChangedListener(
				new TextWatcher() {
					@Override
					public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

					}

					@Override
					public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					}

					@Override
					public void afterTextChanged(Editable editable) {
						try {
							JsonEPVA.this.set(Caster.Default.instance.cast(JsonEPVA.this.field.getType(), JSON.instance.parse(value.getText().toString())));
						} catch (Exception ignored) {
						}
					}
				}
		);

		parent.addView(view);
		return view;
	}
}
