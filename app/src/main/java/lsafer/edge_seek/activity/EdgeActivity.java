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
package lsafer.edge_seek.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.io.Edge;
import lsafer.edge_seek.view.global.perference.BooleanEPVA;
import lsafer.edge_seek.view.global.perference.JsonEPVA;
import lsafer.io.File;
import lsafer.util.JSObject;
import lsafer.view.Refreshable;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings({"JavaDoc"})
public class EdgeActivity extends AppCompatActivity implements Refreshable {
	private Edge edge;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.load(this).ui.theme());
		this.setContentView(R.layout.activity_edge);

		this.edge = new Edge();
		this.edge.putAll((Map<?, ?>) Objects.requireNonNull(this.getIntent().getSerializableExtra("edge")));
		this.edge.remote((File) this.getIntent().getSerializableExtra("file"));
	}

	public void _advanced(View view) {
		View adv = this.findViewById(R.id.preference_adv);
		adv.setVisibility(adv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
	}

	public void _delete(View view) {
		this.edge.delete();
		this.finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.edge.load();
		this.refresh();
	}

	@Override
	public void refresh() {
		this.edge.clear();
		this.edge.load();

		LinearLayout parent = this.findViewById(R.id.preference);
		LinearLayout parent_adv = this.findViewById(R.id.preference_adv);

		parent.removeAllViews();
		parent_adv.removeAllViews();

		this.<TextView>findViewById(R.id.title).setText(this.edge.remote().getName());

		ArrayList<JSObject.Entry<Object, Object>> entries = new ArrayList<>((Set<JSObject.Entry<Object, Object>>) (Object) this.edge.entrySet());
		Collections.sort(entries, (e, e1) -> String.valueOf(e.getKey()).compareTo(String.valueOf(String.valueOf(e1.getKey()))));

		entries.forEach(e -> {
			Object value;
			Class<?> type = e.field == null ? (value = e.getValue()) == null ? Object.class : value.getClass() : e.field.getType();

			if (type == Boolean.class)
				new BooleanEPVA(this, parent, parent_adv, type, this.edge, e.getKey());
			else
				new JsonEPVA(this, parent, parent_adv, type, this.edge, e.getKey());
		});
	}
}
