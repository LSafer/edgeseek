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
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.io.Edge;
import lsafer.edge_seek.service.MainService;
import lsafer.edge_seek.util.Common;
import lsafer.view.Refreshable;

/**
 * An activity to visualize to the user where the activated edges.
 * And navigate hem/her to the edge he/she selects. And also provide.
 * Some shortcut gestures.
 *
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
final public class EdgesActivity extends AppCompatActivity implements Refreshable {
	/**
	 * The {@link EdgeViewAdapter}s that currently viewing.
	 */
	final private ArrayList<EdgeViewAdapter> adapters = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.init(this).ui.<App.UI>load().theme());
		this.setContentView(R.layout.activity_edges);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MainService.restart(this);
		this.refresh();
	}

	@Override
	public void refresh() {
		App.edges.clear();
		App.edges.load();

		this.adapters.forEach(EdgeViewAdapter::dismiss);
		this.adapters.clear();

		App.edges.forEach((k, v)-> {
			if (v instanceof Edge)
				this.adapters.add(new EdgeViewAdapter((Edge) v));
		});
	}

	/**
	 * Create new edge depending on the id of the given view.
	 *
	 * @param view that have called this function
	 */
	public void _new(View view) {
		Edge edge = new Edge();
		edge.position = Common.position(view.getId());
		edge.xposition = Common.xposition(view.getId());
		edge.remote(App.edges.remote().child(Common.positionName(edge.position) + " " + Common.positionName(edge.xposition)));
		edge.save();
		this.refresh();
	}

	/**
	 * An adapter to manage the views. That defined in the xml layout file of this activity.
	 * By hide/show or set listeners on them. All because. It's a little difficult to build views manually each time.
	 */
	private class EdgeViewAdapter {
		/**
		 * The edge data container.
		 */
		private Edge edge;

		/**
		 * The adapted view.
		 */
		private View view;

		/**
		 * Initialize this adapter with the given edge.
		 * And find the view to be adapted automatically
		 * From the data in the given edge.
		 *
		 * @param edge to get data from
		 */
		private EdgeViewAdapter(Edge edge) {
			this.edge = edge;
			this.view = EdgesActivity.this.findViewById(Common.positionId(edge.position, edge.xposition));

			this.view.setVisibility(View.VISIBLE);
			this.view.setBackgroundColor(this.edge.color());
			this.view.setOnClickListener(v -> {
				Intent intent = new Intent(EdgesActivity.this, EdgeActivity.class);
				intent.putExtra("edge", this.edge);
				intent.putExtra("file", this.edge.remote());
				EdgesActivity.this.startActivity(intent);
			});
			this.view.setOnLongClickListener(v -> {
				if (this.edge.xposition == 4)
					this.edge.split();
				else this.edge.merge();

				EdgesActivity.this.refresh();
				return true;
			});
		}

		/**
		 * Hide the adapted view.
		 */
		@SuppressWarnings("UnusedReturnValue")
		private void dismiss() {
			this.view.setVisibility(View.INVISIBLE);
		}
	}
}
