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
import android.provider.Settings;
import android.view.WindowManager;
import cufyx.perference.MapDataStore;
import cufyx.perference.SimplePreferenceActivity;
import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.data.AppData;
import lsafer.edgeseek.service.MainService;

import java.util.Objects;

/**
 * The first activity displayed to the user.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 19-May-2020
 */
final public class MainActivity extends SimplePreferenceActivity implements MapDataStore.OnDataChangeListener {
	@Override
	public void onDataChange(MapDataStore data, String key, Object oldValue, Object newValue) {
		//change listeners

		if (!Objects.equals(oldValue, newValue)) {
			//ignore useless calls

			switch (key) {
				case AppData.THEME:
					this.startActivity(new Intent(this, MainActivity.class));
					this.finish();
					break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.data.getTheme());
		this.setPreferenceDataStore(App.data.store);
		this.setPreferenceLayout(R.xml.preference_app);
		this.setContentView(R.layout.activity_preference);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		//register listener
		App.data.store.registerOnDataChangeListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		//remove listeners
		App.data.store.unregisterOnDataChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		//don't run unless permission granted
		if (!Settings.System.canWrite(this) || !Settings.canDrawOverlays(this)) {
			this.startActivity(new Intent(this, PermissionsActivity.class));
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
				this.startForegroundService(new Intent(this, MainService.class));
			else this.startService(new Intent(this, MainService.class));
		}
	}
}
 