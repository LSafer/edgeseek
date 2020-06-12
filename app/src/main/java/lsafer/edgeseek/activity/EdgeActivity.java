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
import androidx.preference.MultiSelectListPreference;
import cufyx.perference.SimplePreferenceActivity;
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
final public class EdgeActivity extends SimplePreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int position = this.getIntent().getIntExtra("edge", -1);

		super.onCreate(savedInstanceState);
		this.setTheme(App.data.getTheme());
		this.setPreferenceDataStore(App.data.edges.get(position).store);
		this.setPreferenceLayout(R.xml.preference_edge);
		this.setContentView(R.layout.activity_preference);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		//fragment setup
		this.findPreferenceByKey(R.id.fragment, "title")
				.setTitle(Position.edge.getTitle(position));
		MultiSelectListPreference blackList = this.findPreferenceByKey(R.id.fragment, EdgeData.BLACK_LIST);
		blackList.setEntries(UserPackagesUtil.getLabels(this.getPackageManager()));
		blackList.setEntryValues(UserPackagesUtil.getPackagesNames(this.getPackageManager()));
	}
}
