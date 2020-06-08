package lsafer.edgeseek.activity;

import android.os.Bundle;
import android.widget.TextView;

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
	protected void onCreate(@Nullable Bundle savedInstanceState) {
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

		//title
		this.<TextView>findViewById(R.id.title)
				.setText(Position.getSideTitle(this.position));
	}

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
}