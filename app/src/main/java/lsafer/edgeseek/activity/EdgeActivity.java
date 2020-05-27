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

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.fragment.EdgeDataFragment;

/**
 * An activity that customize the edge it focuses on.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
public class EdgeActivity extends AppCompatActivity implements EdgeDataFragment.Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(R.style.Theme_AppCompat);
		this.setContentView(R.layout.activity_edge);

		//edge-data fragment
		this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment_edge, new EdgeDataFragment())
				.commit();
	}

	@Override
	protected void onPause() {
		super.onPause();
		App.data.save();
	}

	@Override
	public EdgeData getEdgeData(EdgeDataFragment fragment) {
		Objects.requireNonNull(fragment, "fragment");
		return App.data.edges.get(this.getIntent().getIntExtra("edge", -1));
	}
}
