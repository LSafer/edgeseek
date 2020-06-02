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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.fragment.EdgeDataFragment;
import lsafer.edgeseek.util.Util;

/**
 * An activity that customize the edge it focuses on.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
public class EdgeActivity extends AppCompatActivity implements EdgeDataFragment.Activity {
	/**
	 * The target edge-position by this activity.
	 */
	private int position;

	@Override
	public EdgeData getEdgeData(EdgeDataFragment fragment) {
		Objects.requireNonNull(fragment, "fragment");
		return App.data.edges.get(this.position);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(Util.theme(App.data.theme));
		this.setContentView(R.layout.activity_edge);

		this.position = this.getIntent().getIntExtra("edge", -1);

		//title
		this.<TextView>findViewById(R.id.title)
				.setText(Util.positionEdgeName(this.position));

		//edge-data fragment
		this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment_edge, new EdgeDataFragment())
				.commit();
	}
}
