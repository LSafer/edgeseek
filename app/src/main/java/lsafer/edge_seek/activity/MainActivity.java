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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.service.EdgesService;
import lsafer.view.Refreshable;

/**
 * Main Activity.
 *
 * @author LSaferSE
 * @version 1
 * @since 4-oct-2019
 */
@SuppressWarnings("JavaDoc")
public class MainActivity extends AppCompatActivity implements Refreshable {
	@StyleRes
	int theme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(theme = App.load(this).ui.theme());
		this.setContentView(R.layout.activity_main);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			this.startForegroundService(new Intent(this, EdgesService.class));
		else this.startService(new Intent(this, EdgesService.class));
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

	public void _autoBrightness(View view) {
		App.main.auto_brightness = !App.main.auto_brightness;
		App.main.save();
		this.refresh();
	}

	public void _boot(View view) {
		App.main.boot = !App.main.boot;
		App.main.save();
		this.refresh();
	}

	public void _edges(View view) {
		this.startActivity(new Intent(this, EdgesActivity.class));
	}

	public void _permissions(View view) {
		this.startActivity(new Intent(this, PermissionsActivity.class));
	}

	public void _power(View view) {
		if(App.main.activated) {
			this.stopService(new Intent(this, EdgesService.class));
			App.main.activated = false;
			App.main.save();
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
				this.startForegroundService(new Intent(this, EdgesService.class));
			else this.startService(new Intent(this, EdgesService.class));
			App.main.activated = true;
			App.main.save();
		}
	}

	public void _ui(View view) {
		this.startActivity(new Intent(this, UIActivity.class));
	}

	@Override
	public void refresh() {
		this.<Switch>findViewById(R.id.power).setChecked(App.main.activated);
		this.<Switch>findViewById(R.id.boot).setChecked(App.main.boot);
		this.<Switch>findViewById(R.id.autoBrightness).setChecked(App.main.auto_brightness);
	}
}
