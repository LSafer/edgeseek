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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.view.Refreshable;

/**
 * An activity to manage user interface preference.
 *
 * @author LSaferSE
 * @version 1 alpha (05-Oct-19)
 * @since 05-Oct-19
 */
final public class UIActivity extends AppCompatActivity implements Refreshable {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.init(this).ui.<App.UI>load().theme());
		this.setContentView(R.layout.activity_ui);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.refresh();
	}

	@Override
	public void refresh() {
		this.<TextView>findViewById(R.id.theme).setText(App.ui.theme);
	}

	/**
	 * Toggle the theme of this application.
	 *
	 * @param view ignored
	 * @see App.UI#theme
	 * @see App.UI#theme()
	 */
	public void _theme(View view) {
		App.ui.theme = App.ui.theme.equals("dark") ? "light" : "dark";
		App.ui.save();

		this.startActivity(new Intent(this, UIActivity.class));
		this.finish();
	}
}
