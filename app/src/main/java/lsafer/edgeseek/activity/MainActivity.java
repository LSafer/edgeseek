/*
 *	Copyright 2020 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package lsafer.edgeseek.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.data.AppData;
import lsafer.edgeseek.fragment.AppDataFragment;
import lsafer.edgeseek.service.EdgeService;
import lsafer.edgeseek.util.Util;

/**
 * The first activity displayed to the user.
 * <p>
 * - starts the service
 * - displays the edges to the user
 * - leads to the edge-activity to customize the edge
 *
 * @author lsafer
 * @version 0.1.5
 * @since 19-May-2020
 */
public class MainActivity extends AppCompatActivity implements AppDataFragment.Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(R.style.Theme_AppCompat);
		this.setContentView(R.layout.activity_main);

		//app-data fragment
		this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment_app, new AppDataFragment())
				.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			this.startForegroundService(new Intent(this, EdgeService.class));
		} else {
			this.startService(new Intent(this, EdgeService.class));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		App.data.save();
	}

	@Override
	public AppData getAppData(AppDataFragment fragment) {
		Objects.requireNonNull(fragment, "fragment");
		return App.data;
	}

	/**
	 * Get invoked when an edge of the screen module displayed to the user get touched.
	 *
	 * @param view the view that has been touch
	 */
	public void onEdgeClick(View view) {
		Intent intent = new Intent(this, EdgeActivity.class);
		intent.putExtra("edge", Util.position(view.getId()));
		this.startActivity(intent);
	}
}
