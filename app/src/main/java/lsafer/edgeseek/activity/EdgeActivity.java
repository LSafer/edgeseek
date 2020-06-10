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
import androidx.preference.Preference;
import androidx.preference.PreferenceDataStore;
import cufyx.perference.SimplePreferenceFragment;
import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.util.Position;
import lsafer.edgeseek.util.UserPackagesUtil;

/**
 * An activity that customize the edge it focuses on.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
final public class EdgeActivity extends AppCompatActivity implements SimplePreferenceFragment.OwnerActivity {
	@Override
	public PreferenceDataStore getPreferenceDataStore(SimplePreferenceFragment fragment) {
		return App.data.edges.get(this.getIntent().getIntExtra("edge", -1)).store;
	}

	@Override
	public int getPreferenceResources(SimplePreferenceFragment fragment) {
		return R.xml.fragment_edge_data;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(App.data.getTheme());
		this.setContentView(R.layout.activity_fragment);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		//fragment setup
		SimplePreferenceFragment fragment = (SimplePreferenceFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment);
		MultiSelectListPreference blackList = fragment.findPreference(EdgeData.BLACK_LIST);
		Preference title = fragment.findPreference("title");
		title.setTitle(Position.edge.getTitle(this.getIntent().getIntExtra("edge", -1)));
		blackList.setEntries(UserPackagesUtil.getLabels(this.getPackageManager()));
		blackList.setEntryValues(UserPackagesUtil.getPackagesNames(this.getPackageManager()));
	}
}
