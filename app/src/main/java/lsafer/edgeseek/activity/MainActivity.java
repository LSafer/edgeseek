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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceDataStore;

import java.util.Objects;

import cufyx.perference.MapDataStore;
import cufyx.perference.SimplePreferenceFragment;
import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.service.MainService;
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
public class MainActivity extends AppCompatActivity implements SimplePreferenceFragment.OwnerActivity, MapDataStore.OnDataChangeListener {
	@Override
	public void onDataChange(MapDataStore data, Object key, Object oldValue, Object newValue) {
		//change listeners

		if (key.equals("theme") && !oldValue.equals(newValue)) {
			//theme change listeners
			this.startActivity(new Intent(this, MainActivity.class));
			this.finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//register listener
		App.data.store.registerOnDataChangeListener(this);

		//initial
		super.onCreate(savedInstanceState);
		this.setTheme(Util.theme(App.data.theme));
		this.setContentView(R.layout.activity_fragment);

		//fragment instance
		this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment, new SimplePreferenceFragment())
				.commit();

		//title
		this.<TextView>findViewById(R.id.title)
				.setText(R.string.edge_seek);

		//start main-service
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			this.startForegroundService(new Intent(this, MainService.class));
		else this.startService(new Intent(this, MainService.class));
	}

	@Override
	protected void onDestroy() {
		//remove listeners
		App.data.store.unregisterOnDataChangeListener(this);

		super.onDestroy();
	}

	@Override
	public int getPreferenceResources(SimplePreferenceFragment fragment) {
		//fragment layout
		Objects.requireNonNull(fragment, "fragment");
		return R.xml.fragment_app_data;
	}

	@Override
	public PreferenceDataStore getPreferenceDataStore(SimplePreferenceFragment fragment) {
		//data store
		Objects.requireNonNull(fragment, "fragment");
		return App.data.store;
	}
}
