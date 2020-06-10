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
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.util.Position;

import java.util.Objects;

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
		//transparent status-bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		//initial
		super.onCreate(savedInstanceState);
		this.setTheme(App.data.getTheme());
		this.setContentView(R.layout.activity_edges);

		this.findViewById(R.id.bottom_side).setOnLongClickListener(this::onSplitLongClick);
		this.findViewById(R.id.left_side).setOnLongClickListener(this::onSplitLongClick);
		this.findViewById(R.id.top_side).setOnLongClickListener(this::onSplitLongClick);
		this.findViewById(R.id.right_side).setOnLongClickListener(this::onSplitLongClick);
	}

	@Override
	protected void onResume() {
		super.onResume();

		//SET `GONE` TO ALL FACTORS
		for (int side : Position.side.ARRAY)
			for (int factor : Position.factor.ARRAY) {
				int split = Position.split.inSide(side, factor);

				this.findViewById(Position.split.getId(split))
						.setVisibility(View.GONE);
			}

		//SET `VISIBLE` TO ACTIVE FACTORS
		for (int i = 0; i < Position.side.ARRAY.length; i++) {
			int side = Position.side.ARRAY[i];
			int factor = App.data.sides.get(i).factor;
			int split = Position.split.inSide(side, factor);

			this.findViewById(Position.split.getId(split))
					.setVisibility(View.VISIBLE);
		}

		//styling
		for (EdgeData data : App.data.edges) {
			View view = this.findViewById(Position.edge.getId(data.position));

			if (data.activated)
				view.setBackgroundColor(Color.argb(150,
						Color.red(data.color),
						Color.green(data.color),
						Color.blue(data.color)));
			else view.setBackground(this.getDrawable(R.drawable.module_inactive_edge));
		}
	}

	/**
	 * Get invoked when an edge of the screen module displayed to the user get touched.
	 *
	 * @param view the view that has been touch.
	 * @throws NullPointerException if the given 'view' is null.
	 */
	public void onEdgeClick(View view) {
		Objects.requireNonNull(view, "view");

		Intent intent = new Intent(this, EdgeActivity.class);
		intent.putExtra("edge", Position.edge.ofId(view.getId()));
		this.startActivity(intent);
	}

	/**
	 * Get invoked when an edge-splitter get long-clicked.
	 *
	 * @param view the view that have been clicked.
	 * @return true if the callback consumed the long click, false otherwise.
	 * @throws NullPointerException if the given 'view' is null.
	 */
	public boolean onSplitLongClick(View view) {
		Objects.requireNonNull(view, "view");

		Intent intent = new Intent(this, SideActivity.class);
		intent.putExtra("side", Position.side.ofId(view.getId()));
		this.startActivity(intent);
		return true;
	}
}
