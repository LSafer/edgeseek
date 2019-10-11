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
import android.widget.Switch;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.service.MainService;
import lsafer.view.Refreshable;

/**
 * Main Activity.
 *
 * @author LSaferSE
 * @version 1
 * @since 4-oct-2019
 */
final public class MainActivity extends AppCompatActivity implements Refreshable {
	/**
	 * The current theme res-id that this {@link MainActivity activity} is set to.
	 */
	@StyleRes
	private int theme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(theme = App.init(this).ui.<App.UI>load().theme());
		this.setContentView(R.layout.activity_main);

		MainService.start(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (this.theme != App.ui.theme()) {
			this.startActivity(new Intent(this, MainActivity.class));
			this.finish();
		} else {
			this.refresh();
		}
	}

	/**
	 * Start the activity {@link EdgesActivity}.
	 *
	 * @param view ignored
	 */
	public void _edges(View view) {
		this.startActivity(new Intent(this, EdgesActivity.class));
	}

	/**
	 * Start the activity {@link MiscActivity}.
	 *
	 * @param view ignored
	 */
	public void _misc(View view) {
		this.startActivity(new Intent(this, MiscActivity.class));
	}

	/**
	 * Start the activity {@link EdgesActivity}.
	 * @param view ignored
	 */
	public void _permissions(View view) {
		this.startActivity(new Intent(this, PermissionsActivity.class));
	}

	/**
	 * Switch the activation status of this application.
	 *
	 * @param view ignored
	 * @see App.Main#activated
	 */
	public void _activation(View view) {
		App.main.activated = !App.main.activated;
		MainService.start(this);
		this.refresh();
	}

	/**
	 * Start the activity {@link EdgesActivity}.
	 *
	 * @param view ignored
	 */
	public void _ui(View view) {
		this.startActivity(new Intent(this, UIActivity.class));
	}

	@Override
	public void refresh() {
		this.<Switch>findViewById(R.id.power).setChecked(App.main.activated);
	}
}
