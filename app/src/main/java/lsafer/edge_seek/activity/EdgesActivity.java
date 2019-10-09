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

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.io.Edge;
import lsafer.edge_seek.service.EdgesService;
import lsafer.edge_seek.util.Common;
import lsafer.view.Refreshable;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings("JavaDoc")
public class EdgesActivity extends AppCompatActivity implements Refreshable {
	private static ArrayList<EdgeViewAdapter> adapters = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.load(this).ui.theme());
		this.setContentView(R.layout.activity_edges);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.refresh();
	}

	@Override
	public void refresh() {
		App.edges.clear();
		App.edges.load();
		adapters.forEach(EdgeViewAdapter::dismiss);
		adapters.clear();
		App.edges.forEach((k, v)-> {
			if (v instanceof Edge)
				adapters.add(new EdgeViewAdapter((Edge) v));
		});
	}

	public void _apply(View view) {
		this.apply();
	}

	public void apply() {
		this.stopService(new Intent(this, EdgesService.class));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			this.startForegroundService(new Intent(this, EdgesService.class));
		else this.startService(new Intent(this, EdgesService.class));
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.apply();
	}

	public void _new(View view) {
		new AlertDialog.Builder(this, R.style.KroovAlertDialogTheme)
				.setItems(R.array.edges, (d, i) -> {
					Edge edge = new Edge();
					edge.remote(App.edges.remote().child(String.valueOf(i)));
					edge.position = i;
					edge.save();
					this.refresh();
				})
				.show();
	}

	@SuppressWarnings("WeakerAccess")
	public class EdgeViewAdapter {
		private Edge edge;

		private View view;

		public EdgeViewAdapter(Edge edge) {
			this.edge = edge;
			this.view = EdgesActivity.this.findViewById(Common.positionId(edge));

			this.view.setOnClickListener(v -> EdgesActivity.this.startActivity(new Intent(EdgesActivity.this, EdgeActivity.class)
					.putExtra("edge", this.edge).putExtra("file", this.edge.remote())));
			this.view.setOnLongClickListener(v -> {
				if (this.edge.xposition == -1)
					this.edge.split();
				else this.edge.merge();

				EdgesActivity.this.refresh();
				return true;
			});

			this.view.setBackgroundColor(Color.parseColor(this.edge.color));
			this.show();
		}

		public void show(){
			this.view.setVisibility(View.VISIBLE);
		}
		public void dismiss(){
			this.view.setVisibility(View.INVISIBLE);
		}
	}
}
