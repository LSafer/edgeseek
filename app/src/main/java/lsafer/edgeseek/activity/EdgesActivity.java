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
import lsafer.edgeseek.util.Util;

/**
 * An activity that makes the user navigate between the preference foreach edge.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 01-Jun-20
 */
public class EdgesActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(Util.theme(App.data.theme));
		this.setContentView(R.layout.activity_edges);
	}

	@Override
	protected void onResume() {
		super.onResume();

		//style-em
		for (int i = 0; i < 4; i++) {
			View view = this.findViewById(Util.id(i));
			EdgeData data = App.data.edges.get(i);

			if (data.activated) {
				view.setBackgroundColor(data.color);
			} else {
				view.setBackgroundColor(Color.TRANSPARENT);
			}
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
		intent.putExtra("edge", Util.position(view.getId()));
		this.startActivity(intent);
	}
}
