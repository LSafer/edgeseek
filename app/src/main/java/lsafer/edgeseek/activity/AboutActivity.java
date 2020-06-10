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
import cufyx.perference.MapDataStore;
import cufyx.perference.SimplePreferenceFragment;
import lsafer.edgeseek.App;
import lsafer.edgeseek.BuildConfig;
import lsafer.edgeseek.R;

import java.util.HashMap;
import java.util.Objects;

/**
 * An activity that shows the user information about the application.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 04-Jun-20
 */
final public class AboutActivity extends AppCompatActivity implements SimplePreferenceFragment.OwnerActivity {
	@Override
	public PreferenceDataStore getPreferenceDataStore(SimplePreferenceFragment fragment) {
		//data store
		Objects.requireNonNull(fragment, "fragment");
		return new MapDataStore(new HashMap());
	}

	@Override
	public int getPreferenceResources(SimplePreferenceFragment fragment) {
		//fragment layout
		Objects.requireNonNull(fragment, "fragment");
		return R.xml.fragment_about;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		//transparent status-bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		//activity
		super.onCreate(savedInstanceState);
		this.setTheme(App.data.getTheme());
		this.setContentView(R.layout.activity_fragment);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SimplePreferenceFragment fragment = (SimplePreferenceFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment);

		fragment.findPreference("version")
				.setSummary(this.getString(R.string._des_pref_ABOUT_VERSION, BuildConfig.VERSION_NAME));
		fragment.findPreference("version_code")
				.setSummary(this.getString(R.string._des_pref_ABOUT_VERSION_CODE, String.valueOf(BuildConfig.VERSION_CODE)));
	}
}
