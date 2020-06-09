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

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceDataStore;

import java.util.Objects;

import cufyx.perference.SimplePreferenceFragment;
import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.util.Position;

/**
 * An activity that manages the data of a side.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 08-Jun-20
 */
final public class SideActivity extends AppCompatActivity implements SimplePreferenceFragment.OwnerActivity {
	/**
	 * The side this activity is targeting.
	 */
	private int position;

	@Override
	public int getPreferenceResources(SimplePreferenceFragment fragment) {
		return R.xml.fragment_side_data;
	}

	@Override
	public PreferenceDataStore getPreferenceDataStore(SimplePreferenceFragment fragment) {
		//data store
		Objects.requireNonNull(fragment, "fragment");
		return App.data.sides.get(this.position).store;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		//transparent status-bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		//data
		this.position = this.getIntent().getIntExtra("side", -1);

		//initial
		super.onCreate(savedInstanceState);
		this.setTheme(App.data.getTheme());
		this.setContentView(R.layout.activity_fragment);

		//fragment instance
		this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment, new SimplePreferenceFragment())
				.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();

		//title
		((SimplePreferenceFragment) this.getSupportFragmentManager()
				.findFragmentById(R.id.fragment))
				.findPreference("title")
				.setTitle(Position.getSideTitle(this.position));
	}
}
