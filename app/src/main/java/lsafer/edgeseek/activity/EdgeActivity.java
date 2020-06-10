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
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.PreferenceDataStore;
import cufyx.perference.SimplePreferenceFragment;
import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.util.Position;
import lsafer.edgeseek.util.UserPackagesUtil;

import java.util.Objects;

/**
 * An activity that customize the edge it focuses on.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
final public class EdgeActivity extends AppCompatActivity implements SimplePreferenceFragment.OwnerActivity {
	/**
	 * The target edge-position by this activity.
	 */
	private int position;

	@Override
	public PreferenceDataStore getPreferenceDataStore(SimplePreferenceFragment fragment) {
		//data store
		Objects.requireNonNull(fragment, "fragment");
		return App.data.edges.get(this.position).store;
	}

	@Override
	public int getPreferenceResources(SimplePreferenceFragment fragment) {
		//fragment layout
		Objects.requireNonNull(fragment, "fragment");
		return R.xml.fragment_edge_data;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//transparent status-bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		//data
		this.position = this.getIntent().getIntExtra("edge", -1);

		//initial
		super.onCreate(savedInstanceState);
		this.setTheme(App.data.getTheme());
		this.setContentView(R.layout.activity_fragment);
	}

	@Override
	protected void onResume() {
		super.onResume();

		SimplePreferenceFragment fragment = (SimplePreferenceFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment);

		//title
		fragment.findPreference("title")
				.setTitle(Position.edge.getTitle(this.position));

		//set the installed application's list
		MultiSelectListPreference preference = fragment.findPreference(EdgeData.BLACK_LIST);
		preference.setEntries(UserPackagesUtil.getLabels(this.getPackageManager()));
		preference.setEntryValues(UserPackagesUtil.getPackagesNames(this.getPackageManager()));
	}
}
