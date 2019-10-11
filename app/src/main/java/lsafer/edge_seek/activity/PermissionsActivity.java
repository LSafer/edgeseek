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

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.view.Refreshable;

/**
 * An activity to manage application permissions.
 *
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
final public class PermissionsActivity extends AppCompatActivity implements Refreshable {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.init(this).ui.<App.UI>load().theme());
		super.setContentView(R.layout.activity_premissions);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void refresh() {
		this.<CheckBox>findViewById(R.id.displayOverOtherApps).setChecked(
				Settings.canDrawOverlays(this)
		);
		this.<CheckBox>findViewById(R.id.writeSystemSettings).setChecked(
				Settings.System.canWrite(this)
		);
		this.<CheckBox>findViewById(R.id.ignoreBatteryOptimizations).setChecked(
				this.getSystemService(PowerManager.class).isIgnoringBatteryOptimizations(this.getPackageName())
		);
		this.<CheckBox>findViewById(R.id.foregroundService).setChecked(
				Build.VERSION.SDK_INT < Build.VERSION_CODES.P ||
				this.checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED
		);
		this.<CheckBox>findViewById(R.id.screenOff).setChecked(
				this.checkSelfPermission(Manifest.permission.RECEIVE_BOOT_COMPLETED) == PackageManager.PERMISSION_GRANTED
		);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.refresh();
	}

	/**
	 * Request a permission depending on the caller view.
	 *
	 * @param view that have called this method
	 */
	@SuppressLint("BatteryLife")
	public void _request(View view) {
		switch (view.getId()) {
			case R.id.ignoreBatteryOptimizations:
				this.startActivity(new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).setData(Uri.parse("package:" + getPackageName())));
				break;
			case R.id.displayOverOtherApps:
				this.startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).setData(Uri.parse("package:" + getPackageName())));
				break;
			case R.id.writeSystemSettings:
				this.startActivity(new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).setData(Uri.parse("package:" + getPackageName())));
				break;
			case R.id.foregroundService:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
					this.requestPermissions(new String[]{Manifest.permission.FOREGROUND_SERVICE}, 611561551);
				break;
			case R.id.screenOff:
				this.requestPermissions(new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, 895623);
				break;
		}
	}
}
