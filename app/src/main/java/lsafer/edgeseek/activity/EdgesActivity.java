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
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.util.Position;

/**
 * An activity that makes the user navigate between the preference foreach edge.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 01-Jun-20
 */
final public class EdgesActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		//initial
		super.onCreate(savedInstanceState);
		this.setTheme(App.data.getTheme());
		this.setContentView(R.layout.activity_edges);

		this.findViewById(R.id.bottom_split).setOnLongClickListener(this::onSplitLongClick);
		this.findViewById(R.id.left_split).setOnLongClickListener(this::onSplitLongClick);
		this.findViewById(R.id.top_split).setOnLongClickListener(this::onSplitLongClick);
		this.findViewById(R.id.right_split).setOnLongClickListener(this::onSplitLongClick);
	}

	@Override
	protected void onResume() {
		super.onResume();

		//SET `GONE` TO ALL FACTORS
		for (int side : Position.SIDES)
			for (int factor : Position.FACTORS)
				this.findViewById(Position.getId(side, factor))
						.setVisibility(View.GONE);

		//SET `VISIBLE` TO ACTIVE FACTORS
		for (int i = 0; i < Position.SIDES.length; i++)
			this.findViewById(Position.getId(i, App.data.sides.get(i).factor))
					.setVisibility(View.VISIBLE);

		//styling
		for (EdgeData data : App.data.edges) {
			View view = this.findViewById(Position.getId(data.position));

			if (data.activated)
				view.setBackgroundColor(Color.argb(150,
						Color.red(data.color),
						Color.green(data.color),
						Color.blue(data.color)));
			else view.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	/**
	 * Get invoked when an edge of the screen module displayed to the user get touched.
	 *
	 * @param view the view that has been touch
	 * @throws NullPointerException if the given 'view' is null
	 */
	public void onEdgeClick(View view) {
		Objects.requireNonNull(view, "view");

		Intent intent = new Intent(this, EdgeActivity.class);
		intent.putExtra("edge", Position.fromId(view.getId()));
		this.startActivity(intent);
	}

	/**
	 * Get invoked when an edge-splitter get long-clicked.
	 *
	 * @param view the view that have been clicked
	 * @return true if the callback consumed the long click, false otherwise.
	 * @throws NullPointerException if the given 'view' is null
	 */
	public boolean onSplitLongClick(View view) {
		Objects.requireNonNull(view, "view");

		Intent intent = new Intent(this, SideActivity.class);
		intent.putExtra("side", Position.fromId(view.getId()));
		this.startActivity(intent);
		return true;
	}
}
