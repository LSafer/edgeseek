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
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.service.MainService;
import lsafer.view.Refreshable;

/**
 * Misc preferences activity.
 *
 * @author LSaferSE
 * @version 1 (alpha 11-oct-2019)
 * @since 11-oct-2019
 */
public class MiscActivity extends AppCompatActivity implements Refreshable {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.init(this).ui.<App.UI>load().theme());
		this.setContentView(R.layout.activity_misc);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.refresh();
	}

	@Override
	public void refresh() {
		this.<Switch>findViewById(R.id.boot).setChecked(App.main.boot);
		this.<Switch>findViewById(R.id.autoBrightness).setChecked(App.main.auto_brightness);
	}

	/**
	 * Toggle {@link App.Main#auto_brightness}.
	 *
	 * @param view ignored
	 */
	public void _autoBrightness(View view) {
		App.main.auto_brightness = !App.main.auto_brightness;
		App.main.save();
		MainService.restart(this);
		this.refresh();
	}

	/**
	 * Toggle {@link App.Main#boot}
	 *
	 * @param view ignored
	 */
	public void _boot(View view) {
		App.main.boot = !App.main.boot;
		App.main.save();
		this.refresh();
	}
}
