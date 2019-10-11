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
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lsafer.edge_seek.R;
import lsafer.edge_seek.constant.Constant;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.io.Edge;
import lsafer.edge_seek.view.abst.EditEntry;
import lsafer.io.File;
import lsafer.util.JSObject;
import lsafer.view.Refreshable;

/**
 * An activity that targets an {@link Edge}.
 * And made it possible for the user to edit it.
 *
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
final public class EdgeActivity extends AppCompatActivity implements Refreshable {
	/**
	 * The edge targeted by this activity.
	 */
	private Edge edge;

	/**
	 * Sections {@link ViewGroup views}.
	 */
	private ViewGroup[] sections;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.init(this).ui.<App.UI>load().theme());
		this.setContentView(R.layout.activity_edge);


		this.sections = new ViewGroup[]{
				this.findViewById(R.id.misc),
				this.findViewById(R.id.advanced),
				this.findViewById(R.id.appearance),
				this.findViewById(R.id.actions),
				this.findViewById(R.id.custom),
				this.findViewById(R.id.hidden)
		};
		this.findViewById(R.id.title).setOnLongClickListener(v -> {
			this.sections[Constant.SECTION_HIDDEN].setVisibility(View.VISIBLE);
			return true;
		});

		this.edge = new Edge();
		this.edge.putAll((Map<?, ?>) Objects.requireNonNull(this.getIntent().getSerializableExtra("edge")));
		this.edge.remote((File) this.getIntent().getSerializableExtra("file"));
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.edge.load();
		this.refresh();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (this.edge.exist())
			this.edge.save();
	}

	@Override
	public void refresh() {
		this.edge.clear();
		this.edge.load();

		this.<TextView>findViewById(R.id.title).setText(this.edge.remote().getName());

		for (ViewGroup section : sections)
			section.removeAllViews();

		ArrayList<JSObject.Entry<Object, Object>> entries = new ArrayList<>((Set<JSObject.Entry<Object, Object>>)(Object) this.edge.entrySet());
		Collections.sort(entries, (e, e1) -> String.valueOf(e.getKey()).compareTo(String.valueOf(e1.getKey())));
		entries.forEach(entry -> EditEntry.newFor(this, sections, entry));
	}

	/**
	 * Delete the targeted {@link Edge} of this.
	 *
	 * @param view ignored
	 */
	public void _delete(View view) {
		this.edge.delete();
		this.finish();
	}
}
